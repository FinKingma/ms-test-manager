# ms-test-manager

## Doel van de microservice
Deze microservice is gebouwd als voorbeeld project om component testen te demonstreren. Zowel event based als rest based wordt binnen dit project gebruikt. En we maken gebruik van een database.

### Functionaliteit
De microservice biedt rest api's om projecten te beheren. 
Daarnaast wordt geluistert naar test resultaat events, die toegevoegd worden aan de projecten.

## Cucumber configuratie
- CucumberIT.java is onze test runner met annotations om cucumber vanuit junit te kunnen draaien
- SpringComponentTest.java bevat de configuratie om spring op te starten voor de component testen
- ApiSteps.java bevat step definitions met rest assured logica
- EventSteps.java bevat step definitions met spring event messaging
- TestdataSteps.java bevat step definitions om testdata te vullen via de Dao's

## Good practises
- ieder scenario moet zijn eigen testdata genereren en unieke testdata gebruiken. Losse sql scripts zorgen na verloop van tijd voor onduidelijkheid welke testdata waarvoor nodig is
- we gebruiken cucumber zodat we rest of event logica in losse bestanden kunnen onderhouden, en de feature files duidelijk blijven voor requirements

## Voordelen van component testing
- Snelle testen die functioneel dekkend (kunnen) zijn
- Minder testen nodig dan als je alles perfect met unit testen zou dekken
- Testen die tegen de API aan testen blijven overeind terwijl de code gerefactored kan worden
- Makkelijk om issues te debuggen door breakingpoints te zetten terwijl je een component test draait
- Met goeie opzet (bijv cucumber) kan je snel een eerste test schrijven die je helpt in development
