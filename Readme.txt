docker kill fuckYouNickBot-master && /
docker rm fuckYouNickBot-master && /
docker rmi $(docker images -q) && /
docker build -f "FuckYouNickBotDockerFile" . && /
docker run -d -f "FuckYouNickBotDockerFile" --name fuckYouNickBot-master
