# Desafio Stefanini

## Configuracao e execucao do projeto
 ### Pre requisitos 
    1. Java JDK 21 instalado;
    2. Git instalado;
    3. IDE instalado (InteliJ, Eclipse, NetBeans ou VSCode);
    4. Sql Server Instalado;
    5. Sql Server Management Studio (SSMS) instalado;

### Passo a passo para instalacao do projeto
1. Acesse o repositorio do projeto Spring no GitHub (https://github.com/MarcelloMenezes/desafio-stefanini.git)
2. Abra o IntelliJ IDEA. Se nao houver projetos abertos, voce vera a tela inicial. Caso contrario, va para File > New > Project from Version Control.
3. Na janela que aparece, cole o link copiado no campo URL e escolha o diretorio onde deseja salvar o projeto.
4. Clique em Clone. O IntelliJ IDEA fara o download do repositorio e o abrira automaticamente.

### Baixar as dependencias
- O IntelliJ IDEA detectara automaticamente as dependencias definidas no pom.xml e as baixara.
- Se nao ocorrer automaticamente, clique com o botao direito no arquivo pom.xml e selecione Add as Maven Project.
- Na janela Maven Projects (geralmente localizada no lado direito), clique em Refresh para garantir que todas as dependencias sejam baixadas corretamente.

### Definindo as Variaveis de Ambiente
- Adicionar as variaveis de ambiente no projeto:
```bash
DB_USERNAME "seu_usuario"
DB_PASSWORD "sua_senha"
DB_URL "jdbc:sqlserver://localhost:1433;databaseName=Desafio;trustServerCertificate=true;"
```

### Migrations para o banco de dados
```sql  
CREATE DATABASE Desafio;
USE Desafio;
CREATE TABLE Tasks (
	id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),
	title NVARCHAR(200) NOT NULL,
	description TEXT,
	status NVARCHAR(100) NOT NULL,
	date_create DATE DEFAULT GETDATE()
);
```

###  Executar o projeto
- Se o projeto for um aplicativo Spring Boot, voce pode executa-lo diretamente:
1. Abra a classe principal do aplicativo (anotada com @SpringBootApplication).
2. Clique com o botao direito na classe e selecione Run.
- O IntelliJ IDEA compilara e iniciara o aplicativo com URL (localhost:8080/tarefas)


## Descricao de endPoints
### GET /tarefas
#### Dados recebidos
```json
[
	{
		"id": 1,
		"description": "Exemplo",
		"title": "Exemplo",
		"status": "Pendente",
        "dateCreate": "29-05-2025"
	},
	{
		"id": 2,
		"description": "Exemplo",
		"title": "Exemplo",
		"status": "Pendente",
        "dateCreate": "29-05-2025"
	}
]
```

### GET /tarefas/{id}
#### Dados recebidos
```json
[
	{
		"id": 1,
		"description": "Exemplo",
		"title": "Exemplo",
		"status": "Pendente",
        "dateCreate": "29-05-2025"
	}
]
```

### POST /tarefas
#### Dados enviados
```json
{
	"title": "Titulo da tarefa",
	"description": "Descricao da tarefa",
    "status": "Pendente"
}
```


### PUT /tarefas/{id}
#### Dados enviados
```json
{
	"title": "Titulo da tarefa editada",
	"description": "Descricao da tarefa editada",
    "status": "Pendente"
}
```
### DELETE /tarefas/{id}

## Ferramentas utilizadas no projeto
- Java
- Spring Boot
- SQL Server
- InteliJ
- Insomnia
- Git