## Exécution du projet

Pour compiler et exécuter correctement le projet avec Maven, suivez les étapes suivantes :

1. Nettoyer le projet :
```
mvn clean
```

2. Compiler le code source :
```
mvn compile
```

3. Générer le package (JAR) :
```
mvn package
```

4. Installer le projet dans le repository local :
```
mvn install
```

Ces commandes permettent respectivement de nettoyer les anciens fichiers, compiler le projet, générer l’artefact et le rendre disponible pour d’autres projets Maven.

## Distribution binaire

Une distribution binaire du projet est fournie dans le dossier `bindist/`.

Pour exécuter le compilateur avec la grammaire que vous avez créée, suivez les étapes suivantes dans un terminal :

1. Se placer dans le dossier contenant l’exécutable :
```
cd bindist/bin
```

2. Lancer l’analyse d’un exemple :
```
./parse ../../samples/Exemple1
```

3. Vous pouvez tester les différents exemples fournis :
```
./parse ../../samples/Exemple2
./parse ../../samples/Exemple3
...
./parse ../../samples/Exemple9
```

Ces commandes permettent d’exécuter directement le compilateur sans passer par Maven, en utilisant les fichiers d’exemples fournis.

## Utilisation

Dans un projet Maven, ajouter la dépendance suivante dans le fichier `pom.xml` :

```
		<dependency>
			<groupId>fr.ul.miage</groupId>
			<artifactId>arbre</artifactId>
			<version>0.0.7</version>
		</dependency>

```
##Auteur

Azim Roussanaly (IDMC/Université de Lorraine)

##Licence

Licence MIT