# This file is used to make a final zip file for submission
# This file requires gradle to be executed

PREFIX=v
VERSION=0.9

CLEAR_OUTPUT=false

echo "cleaning..."
rm -rf final
rm -rf $PREFIX$VERSION
rm -rf $PREFIX$VERSION.zip

echo "creating directories..."
mkdir final
mkdir final/executables
mkdir final/executables/Aufgabe{1..5}
mkdir final/executables/Aufgabe{1..5}/beispiele
mkdir final/code
mkdir final/code/Aufgabe{1..5}

echo "copying all docs..."
cp a{1..5}/Aufgabe*.pdf final/

echo "copying instructions..."
cp INSTRUKTIONEN.md final/
echo "building a2 using gradle..."
(cd a2 && gradle clean && gradle build && gradle jar) &> /dev/null

echo "building a4 using gradle..."
(cd a4 && gradle clean && gradle build && gradle jar) &> /dev/null

for i in {2,4}; do
    echo "copying files from a$i..."
    cp a$i/app/build/libs/app.jar final/executables/Aufgabe$i/a$i.jar
    cp a$i/app/src/main/resources/*.txt final/executables/Aufgabe$i/beispiele/
    cp a$i/app/src/main/java/a$i/*.java final/code/Aufgabe$i/
done

for i in {1,3,5}; do
    echo "copying files from a$i..."
    cp a$i/*.jar final/executables/Aufgabe$i/a$i.jar
    cp a$i/**/*.txt final/executables/Aufgabe$i/beispiele/
    cp a$i/*.java final/code/Aufgabe$i/
done

echo "renaming files..."
mv final $PREFIX$VERSION

echo "creating zip..."
zip -r $PREFIX$VERSION.zip $PREFIX$VERSION/* &> /dev/null

if $CLEAR_OUTPUT; then
    echo "removing temporary folders..."
    rm -rf $PREFIX$VERSION
fi

echo "Done."