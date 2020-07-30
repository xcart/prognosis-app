NAME := prognosis-app
VERSION := 1.0.0
DOCKER_REGISTRY := xcart
CONTAINER := backend

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
        docker run -d --network shared --rm --name $(CONTAINER) $(DOCKER_REGISTRY)/$(NAME):latest

stop:
        docker stop $(CONTAINER)

update: build release

local: update stop run

.PHONY: build install pull clean update release