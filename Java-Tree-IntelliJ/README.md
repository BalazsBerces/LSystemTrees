# Java Tree – L-system visualizer

Java/Swing alkalmazás, amely L-system szabályok alapján fraktál/fa alakzatokat generál és rajzol ki.

## Követelmények

- JDK 17 vagy újabb
- IntelliJ IDEA

## Megnyitás IntelliJ IDEA-ban

1. `File` → `Open`
2. Válaszd ki a projekt gyökérmappáját (`Java-Tree-IntelliJ`).
3. IntelliJ felismeri a `pom.xml` fájlt Maven projektként.
4. Állíts be legalább JDK 17-et: `File` → `Project Structure` → `Project SDK`.
5. Nyisd meg ezt a fájlt:
   `src/main/java/L_System/Main.java`
6. A `main()` melletti zöld nyíllal indítsd el a programot.

## Futtatás

A program indulása után:

1. válassz egy fa/L-system típust,
2. állítsd be a rekurziók számát,
3. kattints a **Generate** gombra.

A program futás közben a projekt aktuális munkakönyvtárába létrehoz egy `Lines.xml` fájlt. Ez generált fájl, ezért a `.gitignore` kizárja a Gitből.

## Projektstruktúra

```text
Java-Tree-IntelliJ/
├── pom.xml
├── README.md
├── .gitignore
├── docs/
│   ├── L_System.pdf
│   └── tételek.pdf
└── src/
    └── main/
        └── java/
            └── L_System/
                ├── Main.java
                ├── MyFrame.java
                ├── LSystem.java
                ├── Line.java
                ├── TreeNode.java
                └── TreeType.java
```

## Feltöltés GitHubra

A projekt gyökérmappájában:

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin <A_GITHUB_REPOSITORY_URL_JE>
git push -u origin main
```

A `<A_GITHUB_REPOSITORY_URL_JE>` helyére a saját üres GitHub repository URL-je kerüljön.

## Megjegyzés

Az eredeti IntelliJ-specifikus `.idea` és `.iml` fájlok nincsenek a projektben, mert ezek gép- és IDE-beállításokat tartalmazhatnak. IntelliJ a `pom.xml` alapján újragenerálja őket.
