MAVEN			= ./mvnw
XMLLINT			= xmllint
DOCKER			= docker
DOCKER_COMPOSE	= docker-compose

COVERAGE_ARGS	= --html --xpath '//table[@id="coveragetable"]/tfoot//td[@class="ctr2"][1]/text()'
COVERAGE_UT		= `$(XMLLINT) $(COVERAGE_ARGS) target/jacoco-ut/index.html`
COVERAGE_IT		= `$(XMLLINT) $(COVERAGE_ARGS) target/jacoco-it/index.html`

DOCKER_ROO				= roo
DOCKER_DB				= mongo mongo-express
DOCKER_DANGLING_IMAGES	= $(shell $(DOCKER) images -f "dangling=true" -q)

V = 0
Q = $(if $(filter 1,$V),,@)
M = $(shell printf "\033[34;1m▶\033[0m")

## Project
clean: ; $(info $(M) ROO => Cleaning project...)
	$(Q) $(MAVEN) clean -q

test: ; $(info $(M) ROO => Starting unit tests...)
	$(Q) $(MAVEN) $(JUPITER_OPTS) test -q -B
	@echo "Coverage: $(COVERAGE_UT)"

verify: ; $(info $(M) ROO => Starting integration tests...)
	$(Q) $(MAVEN) verify -q -B
	@echo "Coverage: $(COVERAGE_IT)"

build: clean ; $(info $(M) ROO => Building project...)
	$(Q) $(MAVEN) package spring-boot:repackage -Dmaven.test.skip=true -q -B

.PHONY: clean test verify build

## Docker

# Clean roo docker resources
docker-clean: ; $(info $(M) DOCKER => Cleaning resources...)	@
	$(Q) $(DOCKER_COMPOSE) down
	$(Q) $(DOCKER) rmi roo:dev

# Removes dangling images created in development when we're override image tag and name
docker-clean-dangling: ; $(info $(M) DOCKER => Cleaning dangling images...)	@
	$(Q) $(DOCKER) rmi -f $(DOCKER_DANGLING_IMAGES)

# Start only roo docker services
docker-start-roo: build ; $(info $(M) ROO => Starting docker roo ...)	@
	$(Q) $(DOCKER_COMPOSE) up -d --build $(DOCKER_ROO)

# Stoping only roo docker services
docker-stop-roo: build ; $(info $(M) ROO => Stoping docker roo ...)	@
	$(Q) $(DOCKER_COMPOSE) stop $(DOCKER_ROO)

# Start only mongo docker services
docker-start-db: ; $(info $(M) ROO => Starting...)	@
	$(Q) $(DOCKER_COMPOSE) up -d --build $(DOCKER_DB)

docker-stop-db: ; $(info $(M) ROO => Starting...)	@
	$(Q) $(DOCKER_COMPOSE) stop $(DOCKER_DB)

# Starts log monitoring for docker containers
docker-monitor: ; $(info $(M) DOCKER => Container monitoring starting...)	@
	$(Q) $(DOCKER_COMPOSE) logs -f

# Start All Docker Services
docker: docker-start-db docker-start-roo docker-monitor ; $(info $(M) Started all docker services ...)	@
