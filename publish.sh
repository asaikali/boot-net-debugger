./mvnw clean package
docker build . -t asaikali/boot-net-debugger
docker push asaikali/boot-net-debugger
