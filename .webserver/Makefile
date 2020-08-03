NAME := prognosis-app-webserver
VERSION := 1.0.0
DOCKER_REGISTRY := xcart
CONTAINER := webserver

build:
	docker build . -t $(DOCKER_REGISTRY)/$(NAME):$(VERSION)

install:
	docker push $(DOCKER_REGISTRY)/$(NAME):$(VERSION)

pull:
	docker pull $(DOCKER_REGISTRY)/$(NAME):$(VERSION)

clean:
	docker rmi -f $(DOCKER_REGISTRY)/$(NAME):$(VERSION)

release:
	docker tag $(DOCKER_REGISTRY)/$(NAME):$(VERSION) $(DOCKER_REGISTRY)/$(NAME):latest
	docker push $(DOCKER_REGISTRY)/$(NAME):latest

run:
	docker run -d --network shared --rm --name $(CONTAINER) -p 80:80 -p 443:443 $(DOCKER_REGISTRY)/$(NAME):latest

stop:
	docker stop $(CONTAINER)

.PHONY: build install pull clean run stop