# ms-team-morale

##Component testing training

####DE APP
- Leg de microservice uit
- Door de code heen lopen
####DATABASE
- com.h2database toevoegen aan pom
- Draaiende applicatie
- test application properties incl datasource en liquibase
- Liquibase scripts
####REST TESTING
- Rest assured toevoegen aan pom
- Poort nummer ophalen
- Basis van rest assured (given(), when(), then())
- Alle teams moeten uniek zijn
- Verschillende manieren om responses te testen (JsonPath return object, of direct in rest assured chain)
- Stuur losse json vs object met mapper
- Test in db vs test met gebruiken van endpoints
- Abstract class om helpers in de verbergen
- Schrijf je eerste rest test
####EVENT TESTING
- spring cloud stream testsupport
- Autowiring the collector, source and sink
- eerste test, insturen van een event zonder dat het team bestaat
- Opzetten testdata (via rest, of direct in de db?)
- Grotere test, combineer testdata met event insturen
- 6 maanden regel test, gebruik parameterized test. Mocking time? Of direct in de db schrijven?
