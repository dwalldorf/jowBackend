default: build

package:
	mvn package -DskipTests -P docker-build

build: package
	docker-compose build

deploy: package
	docker-compose stop -t 3 java
	docker-compose build java
	docker-compose up -d --no-deps java

run:
	docker-compose up -d

run-env: run
	docker-compose stop -t 1 java

run-attach: package
	docker-compose stop -t 3 java
	docker-compose build java
	docker-compose up java

restart: package
	make stop
	docker-compuse build
	docker-compuse up -d

stop:
	docker-compose stop -t 3

down:
	docker-compose stop -t 1
	docker-compose down

test:
	mvn clean test

integration-test:
	mvn clean verify -P integration-test

connect-mongo:
	docker-compose exec mongo mongo owbackend