# Manuális kód átvizsgálás elvégzése az alkalmazás egy részére + Statikus analízis eszköz futtatása és jelzett hibák átnézése

## Issue #3 - Manuális kód átvizsgálás
Először is a kód átvizsgálásával kezdtük. A kód átvizsgálásához megnyitottuk azt IntelliJ-ben, és felosztottuk a fájlokat, és mindenki tüzetesen átnézte a saját fájljait hibák után kutatva.
Ezután különböző brancheken kijavítottuk ezeket, és egymás Pull Requestjeit átnéztük, és ha szükséges volt, akkor észrevételeket tettünk. Ezután, miután minden észrevételt megbeszéltünk, a main-be mergeltük a brancheket.

## Issue #4 - Statikus analízis eszköz futtatása és jelzett hibák átnézése

Github Actions segítségével a projektbe integráltuk a SonarCloud szolgáltatást, és a súlyos hibákat kijavítottuk.
> [!NOTE]
> Nem minden súlyos hibát javítottunk ki. Egyes hibák olyan mélyen gyökereztek a projektben, hogy egy év távlatából azokat megoldani óriási munka lett volna.


