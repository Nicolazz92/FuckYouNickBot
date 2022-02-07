docker rm $(docker stop $(docker ps -a -q --filter ancestor=fuck-you-nick-bot-name:fuck-you-nick-bot-tag)) && /
docker rmi $(docker images fuck-you-nick-bot-name -q) && /
docker build -t "fuck-you-nick-bot-name:fuck-you-nick-bot-tag" . && /
docker run -d -t "fuck-you-nick-bot-name:fuck-you-nick-bot-tag"
