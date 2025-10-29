# --- Estágio 1: Build com Maven ---
# Usamos uma imagem oficial do Maven com JDK 17 para compilar nosso projeto.
FROM maven:3.8-openjdk-17 AS builder

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia apenas o pom.xml para aproveitar o cache de camadas do Docker.
# As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo o código-fonte do projeto.
COPY src ./src

# Copia o arquivo que irá ter a lista de produtos
COPY produtos.csv .

# Executa o build do Maven, pulando os testes, para gerar o arquivo .jar.
RUN mvn package -DskipTests


# --- Estágio 2: Imagem Final de Execução ---
# Usamos uma imagem JRE (Java Runtime Environment) mínima e segura, baseada no Eclipse Temurin.
# Não precisamos do JDK completo para apenas rodar a aplicação.
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho.
WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio 'builder' para a nossa imagem final.
# O nome do JAR é definido pelo <artifactId> e <version> no pom.xml.
COPY --from=builder /app/target/sistemapedidos-0.0.1-SNAPSHOT.jar app.jar

# Copia o arquivo que irá ter a lista de produtos
COPY produtos.csv /app

# Expõe a porta 8080, que é a porta padrão que o Spring Boot usa (e a que configuramos como fallback).
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for executado.
ENTRYPOINT ["java", "-jar", "app.jar"]