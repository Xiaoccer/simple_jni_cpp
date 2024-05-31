set -e
cd $(dirname $0)

(
    cd hello || exit 1
    rm *.so
    g++ -fPIC  -I"../jni8/include" -Wl,--no-undefined,--version-script="./jni_symbol.lds"  -shared -o  hello.so hello.cc
)

bash ./hello_jni/package.sh
