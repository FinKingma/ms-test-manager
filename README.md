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

## Stappen van de training
stappen om component testen op te zetten:
1. Voeg cucumber runner toe met de basic configuratie en de cucumber dependencies (en de spring boot starter test dependency)
   Voeg vervolgens je eerste feature file toe met een eerste scenarios (step defs zijn nog niet nodig) over het maken van een project
   resultaat hoort te zijn dat de applicatie nu niet opstart omdat de database niet gevonden wordt

2. Maak een cucumber application yml aan die de database switched naar H2, laat de componenttestconfiguration gebruik maken van het cucumber profile, en voeg H2 dependencies toe
   resultaat hoort te zijn dat de applicatie nu niet opstart vanwege connectie met kafka

3. Switch de dependency van spring boot starter naar spring-cloud-stream-test-support
   resultaat hoort nu te zijn dat de applicatie opstart en het scenario faalt op missende stappen

4. Maak de missende ApiSteps
   hier de focus op goeie herbruikbare step defs (soms ook hele specifieke step defs nodig, maar dat is hier niet het geval)
   resultaat is dat het scenario nu slaagt

5. Breid de scenarios uit met het ophalen van een project
   zet hier een breakpoint op een stuk code om te kijken wat faalt
   resultaat is dat de applicatie een 500 teruggeeft omdat de dependency service niet gevonden kan worden

6. Voeg wiremock rule toe aan CucumberRunnerTest, de wiremock dependency en de mappings
   resultaat is dat het scenario nu slaagt

7. Voeg nog een extra scenario toe die dekt dat het toevoegen van een scenario uniek moet zijn
   hiervoor moet je een project aangemaakt hebben in de before, hiervoor gaan we de Dao gebruiken
   Hiervoor ga je een nieuwe step def class aanmaken TestDataSteps.java
   resultaat is dat project feature file nu af is

8. Voeg een scenario toe voor het binnenhalen van testdata
   hiervoor moet een nieuwe EventSteps.java toegevoegd worden met de complexe logica om events te kunnen gebruiken

9. Rating scenario toevoegen
   We hebben een mock toegevoegd die 70% als average teruggeeft, 10% is de current tolerance. Zet deze waardes bovenaan in je feature file
   voeg een Scenario Outline toe met 3 examples die een project op GOOD, AVERAGE, en POOR laten uitkomen. Hou hierbij rekening met het gebruik van grenswaardes
   resultaat is dat we nu klaar zijn