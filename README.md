# ms-test-manager

## Doel van de microservice
Deze microservice is gebouwd als voorbeeld project om component testen te demonstreren.
De service bevat de meest complexe technische uitdagingen van component testen:
- event based interactie
- rest based interactie
- een database
- een service waar je afhankelijk van bent

### Functionaliteit
De microservice biedt rest api's om projecten te beheren. 
Daarnaast wordt geluistert naar test resultaat events, die toegevoegd worden aan de projecten.
Vanuit een externe service wordt het 'gemiddelde aantal slagende testen' opgehaald, zodat ieder project een Rating kan krijgen hoe goed deze presteert ten opzichte van het gemiddelde.

## Cucumber configuratie
- CucumberRunnerTest.java is onze test runner met annotations om cucumber vanuit junit te kunnen draaien
- ComponentTestConfiguration.java bevat de configuratie om spring op te starten voor de component testen
- ApiSteps.java bevat step definitions met rest assured logica
- EventSteps.java bevat step definitions met spring event messaging
- TestdataSteps.java bevat step definitions om testdata te vullen via de Dao's

## Good practises
- ieder scenario moet zijn eigen testdata genereren en unieke testdata gebruiken. Losse sql scripts zorgen na verloop van tijd voor onduidelijkheid welke testdata waarvoor nodig is
- we gebruiken cucumber zodat we rest of event logica in losse bestanden kunnen onderhouden, en de feature files duidelijk blijven voor requirements, en makkelijker uitbreidbaar zijn.

## Voordelen van component testing
- Snelle testen die functioneel dekkend (kunnen) zijn
- Minder testen nodig dan als je alles perfect met unit testen zou dekken
- Testen die tegen de API aan testen blijven overeind terwijl de code gerefactored kan worden
- Makkelijk om issues te debuggen door breakingpoints te zetten terwijl je een component test draait
- Met goeie opzet (bijv cucumber) kan je snel een test toevoegen die je helpt in development


## Draaien van het project

### api interface genereren
We genereren de interface en models obv specs.yml in het project. Hierdoor moet je altijd eerst `mvn compile` uitvoeren voordat je de testen kunt draaien.

### Dependencies voor lokaal starten
#### Lokaal opstarten van kafka
https://kafka.apache.org/quickstart

#### Lokaal starten van mysql
https://dev.mysql.com/doc/mysql-getting-started/en/

#### Niet bestaande dependency service
Het project heeft ook op http://localhost:9000 een service nodig die een AverageTestResults terug kan geven. Deze service bestaat niet, waardoor het ophalen van projecten een 500 gaat opleveren.
De microservice kan wel opgestart worden zonder deze service, je krijgt de 500 errors pas zodra je rest calls doet.

#### Opstarten van het project
`mvn spring-boot:run`

### Draaien van alle testen
`mvn test`