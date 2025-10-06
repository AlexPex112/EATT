# Projektübersicht — Logistik-App

Kurzbeschreibung
-----------------
Die Logistik-App ist eine kleine Desktop-Anwendung (Java 11, Swing) zur Demonstration grundlegender Lagerverwaltungsfunktionen (CRUD) mit persistenter Speicherung in SQLite. Ziel des Projekts ist ein klar strukturiertes, lehrbares Beispiel für MVC-Architektur, einfache Datenbankzugriffe und UI-Interaktion.

Architektur und zentrale Komponenten
-----------------------------------
- Architektur: klassisches Model-View-Controller (MVC)
  - Model: `com.example.logistik.model` — Datenzugriff und Domänenmodell (`DatabaseManager`, `Product`).
  - View: `com.example.logistik.view` — Swing-basierte UI (`MainView`).
  - Controller: `com.example.logistik.controller` — Koordination, Event-Handling (`MainController`).

- Wichtige Klassen und Verantwortlichkeiten
  - `App.java`: Programmstart, Look-and-Feel und UI-Initialisierung.
  - `DatabaseManager.java`: SQLite-Verbindung, Tabellen-Erstellung, CRUD-Operationen (`getAllProducts`, `searchProducts`, `addProduct`, `updateProduct`, `getProductById`).
  - `Product.java`: Einfaches POJO (id, itemNumber, itemDescription, quantity, hall, rack, shelf, serialNumber).
  - `MainView.java`: JFrame mit JTable, Suchfeld und Buttons (Add, Edit, Search). Tabelle enthält intern die ID-Spalte (versteckt) zur Zuordnung.
  - `MainController.java`: Verarbeitet Benutzerinteraktionen, öffnet Dialoge, validiert Eingaben, ruft Model-Methoden auf.

Datenmodell (Kurz)
------------------
Tabelle `inventory` (automatisch erzeugt):
- `id` INTEGER PRIMARY KEY AUTOINCREMENT
- `item_number` TEXT NOT NULL
- `item_description` TEXT
- `quantity` INTEGER NOT NULL
- `hall`, `rack`, `shelf` TEXT
- `serial_number` TEXT UNIQUE

Designentscheidungen und Begründung
----------------------------------
- SQLite: einfache, dateibasierte Persistenz für Lehrzwecke; kein Server nötig.
- Swing (java.desktop): klassisches Desktop-Toolkit, ausreichend für einfache Lehr-UIs.
- MVC: klare Trennung von UI, Business-Logik und Datenzugriff erleichtert Erweiterung und Tests.
- ID-Spalte in JTable (versteckt): einfache Möglichkeit, UI-Auswahl mit Datenbankzeilen zu verknüpfen.

Build & Ausführung (Kurz)
-------------------------
Voraussetzung: JDK 11+ und Maven installiert.

Kompilieren:
```sh
mvn clean compile
```

Starten (führt die GUI aus):
```sh
mvn exec:java -Dexec.mainClass=com.example.logistik.App -Dexec.jvmArgs="--add-modules java.desktop"
```

Hinweis: In `pom.xml` ist das `maven-compiler-plugin` so konfiguriert, dass `java.desktop` während des Kompilierens berücksichtigt wird. Wenn auf einem System Probleme mit Swing-Klassen auftreten, sollte `JAVA_HOME` auf eine vollständige JDK-Installation zeigen.

Bekannte Einschränkungen
------------------------
- Keine Lösch-Funktion (kann ergänzt werden).
- Grundlegende Validierung (z. B. Pflichtfelder) vorhanden, aber noch erweiterbar.
- `serial_number` ist UNIQUE; leere Strings werden derzeit als Strings gespeichert (nicht als NULL).

Testbarkeit und Qualität
------------------------
- `DatabaseManager` lässt sich gut unit-testen (z. B. mit in-memory SQLite / temporärer Datei).
- UI-Tests sind nicht enthalten (manuelle Interaktion erwartet).

Vorschläge für Erweiterungen (pädagogisch relevant)
-------------------------------------------------
- Unit-Tests für `DatabaseManager` (JUnit + temporäre DB-Datei)
- Lösch-Funktion und Bestätigungsdialog
- Robustere Validierung und Fehler-Feedback im UI
- CSV-Export/Import zur Datenübernahme
- UI-Tests mit Robot-Framework oder AssertJ-Swing

Kurzfazit
---------
Das Projekt ist als kompaktes Lehrbeispiel konzipiert: es demonstriert Datenzugriff mit JDBC/SQLite, die Integration einer Desktop-UI (Swing) und die Anwendung des MVC-Musters. Der Code ist leicht erweiterbar und kann als Grundlage für Laboraufgaben (z. B. Implementierung von Löschfunktion, Tests oder besseren Validierungen) dienen.

Cross-Build: Windows EXE ohne lokalen Windows-Rechner
---------------------------------------------------
Wenn du auf macOS (oder Linux) arbeitest, kannst du trotzdem eine Windows-.exe erzeugen, indem du eine CI-Job auf einem Windows-Runner ausführst (GitHub Actions). Im Repository habe ich eine Beispiel-Workflow-Datei angelegt: `.github/workflows/build-windows.yml`.

Kurzbeschreibung des Workflows:
- Läuft auf `windows-latest` (GitHub Actions)
- Setzt JDK 17 und baut das Projekt (`mvn package`) — erzeugt ein `*-shaded.jar`.
- Verwendet `jpackage` auf dem Windows-Runner, um ein natives `.exe` zu erstellen.
- Lädt das erzeugte `.exe` als Artifact hoch, das du herunterladen kannst.

Benutzung:
1. Commit & push die Änderungen in dein Repository (z. B. Branch `main`).
2. Öffne die GitHub Actions Seite des Repos und starte den Workflow `Build Windows EXE` manuell oder pushe in den Branch.
3. Nach Abschluss findest du das `LogistikApp.exe` als Download im Actions-Artifacts.

Hinweis: Alternativ kann man lokal `jpackage` auf einem Windows-Host (oder VM) verwenden. Die CI-Variante erspart dir aber einen lokalen Windows-Build.

Automatische Release-Erstellung
--------------------------------
Der CI-Workflow erstellt nach erfolgreichem Build automatisch einen GitHub Release (Tag `v1.0-<run>`) und hängt die erzeugte `LogistikApp-<run>.exe` als Release-Asset an. Du findest den Release auf der GitHub-Releases-Seite deines Repositories.

Triggern des Workflows
----------------------
- Push in `main` oder `master` startet den Workflow automatisch.
- Alternativ kannst du den Workflow manuell aus der Actions-Seite starten (`workflow_dispatch`).