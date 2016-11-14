default: build

redeploy:
	docker-compose stop java
	mvn package -DskipTests
	docker-compose build java
	docker-compose up -d java

build: package
	docker-compose build

run:
	docker-compose up -d

recreate: package
	docker-compose down
	docker-compuse build
	docker-compuse up -d

stop:
	docker-compose down

package:
	mvn package