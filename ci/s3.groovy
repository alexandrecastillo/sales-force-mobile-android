def upload(dir, buildType) {
    withAWS(credentials: "aws_apps_credentials", region: "us-west-2") {
        def date = new Date().format("yyyyMMdd")
        s3Upload(bucket: "belcorp-apps/${dir}/businesspartners/${buildType}/${date}", includePathPattern: "**/*.aab", workingDir: "app/build/outputs/bundle/businesspartners${buildType.capitalize()}", acl: "PublicRead")
        s3Upload(bucket: "belcorp-apps/${dir}/multiprofile/${buildType}/${date}", includePathPattern: "**/*.aab", workingDir: "app/build/outputs/bundle/multiprofile${buildType.capitalize()}", acl: "PublicRead")

        s3Upload(bucket: "belcorp-apps/${dir}/businesspartners/${buildType}/${date}", includePathPattern: "**/mapping.txt", workingDir: "app/build/outputs/mapping/businesspartners${buildType.capitalize()}", acl: "PublicRead")
        s3Upload(bucket: "belcorp-apps/${dir}/multiprofile/${buildType}/${date}", includePathPattern: "**/mapping.txt", workingDir: "app/build/outputs/mapping/multiprofile${buildType.capitalize()}", acl: "PublicRead")
        
        s3Upload(bucket: "belcorp-apps/${dir}/businesspartners/${buildType}/${date}", includePathPattern: "**/*.apk", workingDir: "app/build/outputs/bundle/businesspartners${buildType.capitalize()}", acl: "PublicRead")
        s3Upload(bucket: "belcorp-apps/${dir}/multiprofile/${buildType}/${date}", includePathPattern: "**/*.apk", workingDir: "app/build/outputs/bundle/multiprofile${buildType.capitalize()}", acl: "PublicRead")
    }
}

def download(dir, buildType, path) {
    withAWS(credentials: "aws_apps_credentials", region: "us-west-2") {
        def date = new Date().format("yyyyMMdd")
        s3Download(file: "${path}/businesspartners/", bucket: "belcorp-apps", path: "${dir}/businesspartners/${buildType}/${date}/", force: true)
        s3Download(file: "${path}/multiprofile/", bucket: "belcorp-apps", path: "${dir}/multiprofile/${buildType}/${date}/", force: true)
    }
}

def uploadReports(dir, buildType) {
    withAWS(credentials: "aws_apps_credentials", region: "us-west-2") {
        def date = new Date().format("yyyyMMdd")
        def modules = [
            "app", "core", "components", "messaging", "brightpath", "auth", "kpis", "developmentpath", "virtualmethodology",
            "developmentmaterial", "postulants", "consultants", "orders", "billing", "creditinquiry", "calculator", "termsconditions"
        ]
        modules.each {
            try {
                s3Upload(file: "${it}/build/reports/tests/testBusinesspartnersDevelopUnitTest", bucket: "belcorp-apps", path: "${dir}/reports/${buildType}/${date}/${it}", acl: "PublicRead")
                println("https://s3-us-west-2.amazonaws.com/belcorp-apps/${dir}/reports/${buildType}/${date}/${it}/index.html")
            } catch (Exception ignored) {
                println("Report Not Found: ${it}")
            }
        }
    }
}

return this
