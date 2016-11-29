default: build

package:
	mvn package -DskipTests -P docker-build

build: package
	docker-compose build

deploy: package
	docker-compose stop java
	docker-compose build java
	docker-compose up -d java

run: package
	docker-compose up -d

run-attach: package
	docker-compose stop java
	docker-compose build java
	docker-compose up java

restart: package
	docker-compose down
	docker-compuse build
	docker-compuse up -d

stop:
	docker-compose down

test:
	mvn clean test

integration-test:
	mvn clean verify -P integration-test