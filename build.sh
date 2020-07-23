docker build -t prognosis-app . && docker run -it -p 8080:8080 --env-file ./env.list prognosis-app

#docker run -d --env YOUTRACK_TOKEN= -p 80:8080 xcart/prognosis-app