# This file is used to make a final zip file for submission
# This file requires gradle to be executed
# It will not work on a non Unix based OS

PREFIX=v
VERSION=1.0

CLEAR_OUTPUT=false

echo "cleaning..."
rm -rf final
rm -rf $PREFIX$VERSION
rm -rf $PREFIX$VERSION.zip

echo "creating directories..."
mkdir final
mkdir final/Aufgabe{1..5}
mkdir final/Aufgabe{1..5}/executables
mkdir final/Aufgabe{1..5}/executables/beispiele
mkdir final/Aufgabe{1..5}/code

echo "copying all docs..."
cp a{1..5}/docs/Aufgabe*.pdf final/

echo "copying instructions..."
cp INSTRUKTIONEN.md final/

for i in {2,4}; do
    echo "building a$i using gradle..."
    (cd a$i && gradle clean && gradle build && gradle jar) &> /dev/null

    echo "copying files from a$i..."
    cp a$i/app/build/libs/app.jar final/Aufgabe$i/executables/a$i.jar
    cp a$i/app/**/*.txt final/Aufgabe$i/executables/beispiele/
    cp a$i/app/src/main/java/a$i/*.java final/Aufgabe$i/code/
done

for i in {1,3,5}; do
    echo "attempting to build a$i using just..."
    (cd a$i && just jar) &> /dev/null


    echo "copying files from a$i..."
    cp a$i/bin/target/*.jar final/Aufgabe$i/executables/a$i.jar
    cp a$i/**/*.txt final/Aufgabe$i/executables/beispiele/
    find a$i -mindepth 0 -type f -name "*.java" -exec cp {} final/Aufgabe$i/code/ \;
done

echo "renaming files..."
mv final $PREFIX$VERSION

echo "creating zip..."
(cd $PREFIX$VERSION &&  zip -r -l ../$PREFIX$VERSION.zip . &> /dev/null)

if $CLEAR_OUTPUT; then
    echo "removing temporary folders..."
    rm -rf $PREFIX$VERSION
fi

echo "Done."