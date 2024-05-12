# Build keretrendszer beüzemelése, ha még nincs (Maven, Gradle...) + CI beüzemelése, ha még nincs (GitHub Actions, AppVeyor, Azure Pipelines...)

## Issue #1 - Build keretrendszer beüzemelése - Gradle
A csapattal közösen döntöttük el, hogy Gradle segítségével valósítjuk meg a build keretrendszert. Ennek fő oka az volt, hogy a Gradle egy igen elterjedt, platformfüggetlen és egyszerűen implementálható build eszköz, ami nagyon sok IDE-ben megtalálható beépített feature vagy extension-ként.

> [!NOTE]
> Eddig nem volt még tapasztalatom, hogyan kell egy már meglévő projekthez ilyet csinálni. 

Többszöri próbálkozás és rengeteg Gradle dokumentáció olvasás után arra jutottam, hogy csinálok egy teljesen új - már Gradle keretrendszerrel induló - projektet, majd a meglévő forrásfájlokat a megfelelő helyre bemásoltam.

> [!TIP]
> A projekt megnyitása után a a Main.java függvényt elindítva azonnal megkezdődik a szükséges fájlok letöltése, majd buildelése.

> [!IMPORTANT]
> JAVA SDK: 17, 
> Gradle version: 8.7

## Issue #2 - CI környezet beüzemelése - GitHub Actions
A Gradle beüzemelése után úgy döntöttünk, hogy a CI környezetet GitHub Actions segítségével valósítjuk meg. Elsősorban azért döntöttünk mellette, mert a GitHub Actions a GitHub részeként könnyen elérhető és beüzemelhető, illetve a népszerű fejlesztőkörnyezetekhez letölthető plugin-ként vagy extension-ként.

> [!NOTE]
> Gradle keretrendszerhez nem használtam még CI környezetet, de néhány próba és egy kevés dokumentáció segítségével sikerült megvalósítani.

> [!IMPORTANT]
> Environment: Windows (windows-latest)