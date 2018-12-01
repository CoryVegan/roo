MAVEN 		= ./mvnw
XMLLINT 	= xmllint
DOCKER  	= docker
DOCKER_COMPOSE	= docker-compose

COVERAGE_ARGS 	= --html --xpath '//table[@id="coveragetable"]/tfoot//td[@class="ctr2"][1]/text()'
COVERAGE_UT 	= `$(XMLLINT) $(COVERAGE_ARGS) target/jacoco-ut/index.html`
COVERAGE_IT 	= `$(XMLLINT) $(COVERAGE_ARGS) target/jacoco-it/index.html`

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
