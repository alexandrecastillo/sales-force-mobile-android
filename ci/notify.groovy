def getColor(buildStatus) {
    def colorCode

    if (buildStatus == "STARTED") {
        colorCode = "#FFFF00"
    } else if (buildStatus == "SUCCESS") {
        colorCode = "#00FF00"
    } else if (buildStatus == "ABORTED" || buildStatus == "STOPPED" || buildStatus == "UNSTABLE") {
        colorCode = "#949393"
    } else {
        colorCode = "#FF0000"
    }

    colorCode
}

def send(String buildStatus = "STARTED") {
    def colorCode = getColor(buildStatus)
    def date = new Date().format("yyyy-MM-dd HH:mm:ss")

    def message = """
  BUILD $buildStatus:
  ${env.JOB_NAME} [${env.BUILD_NUMBER}]
  BUILD DATE:
  ${date}
  BUILD URL:
  https://devops.belcorp.biz/job/${env.BUILD_ID}?name=${env.JOB_NAME}
  GIT BRANCH:
  ${env.GIT_BRANCH}
  GIT AUTHOR:
  ${env.CHANGE_AUTHOR_EMAIL}
  GIT COMMIT:
  ${env.GIT_COMMIT}
  """

    sendSlack(colorCode, message, "#jenkinsapp", "ffvv-belcorp", "slack_ffvv_token")
}

def sendAppUrl(appName, url, String buildStatus = "STARTED") {
    def colorCode = getColor(buildStatus)

    def message = "BUILD $buildStatus: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}] - \nAPP $appName \nURL: $url"
    sendSlack(colorCode, message, "#jenkinsapp", "ffvv-belcorp", "slack_ffvv_token")
}

def sendSlack(color, message, channel, domain, token) {
    slackSend(
        color: color,
        message: message,
        channel: channel,
        teamDomain: domain,
        tokenCredentialId: token
    )
}

def sendEmail(from, buildStatus) {
    emailext(
        from: from,
        subject: "BUILD ${buildStatus}: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
        body: '${JELLY_SCRIPT,template="log"}',
        to: '$DEFAULT_RECIPIENTS',
        attachLog: true,
        compressLog: true
    )
}

return this
