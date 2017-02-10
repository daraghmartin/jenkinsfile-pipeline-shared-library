import groovy.json.JsonOutput

def call(body) {
  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  if (!(config.attachments || config.text)) throw new Exception("need text or attacment or both ${config}")

  res = libraryResource 'X'
  slack_defaults = new groovy.json.JsonSlurperClassic().parseText(res)
  config = slack_defaults << config

  def payload_json

  message = [
              text : config.text, 
              channel : config.channel, 
	      username : config.username, 
	      icon_emoji : config.icon_emoji
	    ]

  message.attachments = config.attachments ?: []
  message.text = config.text ?: null

  payload_json = JsonOutput.toJson(message)

  def payload = "payload=${payload_json}"
  helper = com.iag.elcid.pipeline.Helper 
  helper.postIt(config.slack_url, payload, config.proxy_host)

}

