default: build

redeploy:
	docker-compose stop java
	mvn package -DskipTests
	docker-compose build java
	docker-compose up -d java

build: package
	docker-compose build

recreate: package
	docker-compose down
	docker-compuse build
	docker-compuse up -d

package:
	mvn package