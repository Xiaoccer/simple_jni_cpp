set -e
cd $(dirname $0)

rm -rf src/main/resources target
dest="src/main/resources/my/hello/Hello.so"
mkdir -p $(dirname $dest)
cp ../hello/hello.so $dest

mvn package

java -cp target/*.jar my.hello.Hello
