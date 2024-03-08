pipeline {
    agent {
        label getLabel()
    }

    parameters {
        string(
            name: 'SONARQUBE_CONFIG_ID',
            defaultValue: 'Sonar Qube Server',
            description: 'The ID of the SonarQube Configuration to use')
        string(name: 'DEPLOY_TO', defaultValue: '', description: '')
        string(name: 'DEPLOY_COMMIT', defaultValue: '', description: '')
    }

    environment {
        BUILD_TYPE = buildType()
        AWS_ACCOUNT_ID = '820233355588'
        AWS_REGION = 'us-east-1'
        ANDROID_VERSION = '28.0.3-jdk11.150bab4c148700600996e52654ba8fe722612378' // 29.0.1
        FASTLANE_VERSION = '2.142.0' // 131
    }

    stages {
        stage("Initialize") {
            steps {
                script {
                    echo "${BUILD_TYPE}"
                    notifyBuild()
                    notifyBuildDevOps()
                    config()
                    S3 = load "ci/s3.groovy"
                }
            }
        }

        stage("Build Using JDK 11") {
            when {
                expression { enableBuild() }
            }
            steps {
                script {
                    withAndroid {


                        sh "./gradlew bundle${BUILD_TYPE.capitalize()}"

                        def brands = [
                            "businesspartners",
                            "multiprofile"
                        ]

                        brands.each { brand ->
                            sh "chmod 777 -R app/build/outputs/bundle/${brand}${BUILD_TYPE.capitalize()}/"
                            sh "./bundletool --build-apk ${brand}${BUILD_TYPE.capitalize()}"
                        }

                        sh "find . -name mapping.txt"

                        S3.upload("salesforce", "${BUILD_TYPE}")

                    }

                    def nameBusinesspartners = getApkFile('app/build/outputs/bundle', 'businesspartners')
                    def nameMultiprofile = getApkFile('app/build/outputs/bundle', 'multiprofile')

                    notifyUrl("Gestiona tu Negocio", "businesspartners", nameBusinesspartners)
                    notifyUrl("Crecer es Ganar", "multiprofile", nameMultiprofile)

                }
            }
        }

        stage("Deploy") {
            when {
                expression { enableDeploy() }
            }
            steps {
                script {
                    S3.download("salesforce", "${BUILD_TYPE}", "tmp")

                    def (aabBusinesspartners, mappingBusinesspartners) = getBundleFiles('tmp', 'businesspartners')
                    def (aabMultiprofile, mappingMultiprofile) = getBundleFiles('tmp', 'multiprofile')

                    withFastlane {

                        String command = commandToDeploy()

                        if (command) {
                            sh "fastlane android $command marca:'businesspartners' aab:'$aabBusinesspartners' mapping:'$mappingBusinesspartners'"
                            sh "fastlane android $command marca:'multiprofile' aab:'$aabMultiprofile' mapping:'$mappingMultiprofile'"
                        }
                    }
                }
            }
        }

    }

    post {
        always {
            script {
                notifyBuild(currentBuild.result)
                notifyBuildDevOps(currentBuild.result)
                cleanWs()
            }
        }

        unstable {
            script {
                echo 'This will run only if the run was marked as unstable'
            }
        }
    }
}

/* ──────────────────────────────────────────────────────── */

def isMaster() {
    return env.BRANCH_NAME == "master"
}

def isStage() {
    return env.BRANCH_NAME.contains("release")
}

def isDevelop() {
    return env.BRANCH_NAME == "develop"
}

def isReview() {
    return env.BRANCH_NAME == "review"
}

def enableDeploy() {
    return isDevelop() || isReview() || isStage() || (isMaster() && params.DEPLOY_TO == 'store')
}

def enableBuild() {
    return isDevelop() || isReview() || ((isStage() || isMaster()) && params.DEPLOY_TO != 'store')
}

def enableCodeAnalysis() {
    return isDevelop() || isReview() || ((isStage() || isMaster()) && params.DEPLOY_TO != 'store')
}

def commandToDeploy() {
    return isReview() ? 'share' : (isDevelop() ? 'internal' : (isStage() ? 'beta' : (isMaster() ? 'release' : null)))
}

def buildType() {
    return isDevelop() ? 'develop' : (isStage() ? 'stage' : (isMaster() ? 'release' : 'review'))
}

def getSonarProjectEnv() {
    return (isDevelop() || isReview()) ? 'dev' : (isStage() ? 'qas' : null)
}

def getShortCommitId() {
    def gitCommit = env.GIT_COMMIT
    def shortGitCommit = "${gitCommit[0..6]}"
    return shortGitCommit
}

def config() {
    def CONFIG_URL = "https://s3-us-west-2.amazonaws.com/belcorp-apps/keys"
    def CONFIG_PROP = "key.properties"
    def CONFIG_KEY = "release.jks"

    sh "chmod +x ./gradlew"
    sh "mkdir -p config"
    sh "curl -o config/$CONFIG_PROP $CONFIG_URL/release/$CONFIG_PROP"
    sh "curl -o config/$CONFIG_KEY $CONFIG_URL/release/$CONFIG_KEY"
    sh "curl -o config/google_play.json $CONFIG_URL/google_play.json"
    sh "curl -o config/bundletool.jar $CONFIG_URL/bundletool.jar"
}

def withAndroid(unit) {

    sh "\$(aws ecr get-login --no-include-email --region ${AWS_REGION})"
    def image = docker.image("${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/devops/android:${ANDROID_VERSION}")
    image.inside("-u 0") {
        withEnv(["CI=true"]) {
            unit()
        }
    }
}

def withFastlane(unit) {
    sh "\$(aws ecr get-login --no-include-email --region ${AWS_REGION})"
    def image = docker.image("${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/devops/fastlane:${FASTLANE_VERSION}")

    image.inside("-u 0") {
        withEnv(["CI=true"]) {
            unit()
        }
    }
}

/* ──────────────────────────────────────────────────────── */

def notifyBuild(String buildStatus = "STARTED") {
    withFastlane {
        sh "fastlane android notifyBuild status:'$buildStatus' build_id:'${env.BUILD_ID}' job_name:'${env.JOB_NAME}'"
    }
}

def notifyUrl(appName, perfil, app) {
    withFastlane {
        def date = new Date().format("yyyyMMdd")
        def url = "https://s3-us-west-2.amazonaws.com/belcorp-apps/salesforce/$perfil/${BUILD_TYPE}/$date/$app"
        sh "fastlane android notifyUrl name:'$appName' url:'$url'"
    }
}

def getApkFile(String path, String brand) {
    def apk = findFiles(glob: "**/${path}/${brand}${BUILD_TYPE.capitalize()}/**/*.apk")
    def x = apk.length - 1
    return apk[x].name
}

def getBundleFiles(String path, String brand) {
    def aab = findFiles(glob: "**/${path}/${brand}/**/*.aab")
    def mappings = findFiles(glob: "**/${path}/${brand}/**/mapping.txt")
    def x = aab.length - 1
    return [aab[x].path, mappings[0].path]
}

def notifyBuildDevOps(String buildStatus = 'STARTED') {
    buildStatus = buildStatus ?: 'SUCCESS'
    String buildPhase = (buildStatus == 'STARTED') ? 'STARTED' : 'FINALIZED'
    commit = (buildStatus == 'STARTED') ? 'null' : sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'")

    sh """curl -H "Content-Type: application/json" -X POST -d '{
        "name": "${env.JOB_NAME}",
        "type": "pipeline",
        "build": {
            "phase": "${buildPhase}",
            "status": "${buildStatus}",
            "number": ${env.BUILD_ID},
            "scm": {
                "commit": "${commit}"
            },
            "artifacts": {}
        }
    }' https://devops.belcorp.biz/jenkins/webhook"""
}

def getLabel() {
    def configuration = jenkins.model.Jenkins.instance.getItem(env.JOB_NAME.minus("/${env.JOB_BASE_NAME}"))
    return (configuration.getDescription() != '') ? configuration.getDescription() : 'ec2-linux-spot-slave'
}
