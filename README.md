<div align="center">


<h1> Trabalho Integrador | Back End I | Certified Tech Developer | Digital House</h1> 
<p>

Colaboradores: 
<a href="https://www.linkedin.com/in/evertonpdasilva/" target="_blank" rel="noopener noreferrer">Everton Silva</a> |
<a href="https://www.linkedin.com/in/felipe-stefani-a35185116/" target="_blank" rel="noopener noreferrer">Felipe Stefani</a> |
<a href="https://www.linkedin.com/in/gabriella-faria-3665a11b8/" target="_blank" rel="noopener noreferrer">Gabriella Faria</a> |
 <a href="https://www.linkedin.com/in/mariana-de-moraes-orsi-762165224/" target="_blank" rel="noopener noreferrer">Mariana Orsi</a> | 
 <a href="https://www.linkedin.com/in/sabrina-freiberger/" target="_blank" rel="noopener noreferrer">Sabrina Freiberger</a>
 
Expectadores: <a href="#" target="_blank" rel="noopener noreferrer">Juliana Ruama</a> |
<a href="https://www.linkedin.com/in/roger-ricco-rogerio-p-silva-5a888060/" target="_blank" rel="noopener noreferrer">Rogério P. Silva</a>


</p></div>
 
 <h1>Sistema de reserva de consultas</h1>
 <h2>Proposta</h2>
 <p>Desejamos implementar um sistema que permita administrar a reserva/marcação
de consultas para uma clínica odontológica. Os requisitos que devem ser
atendidos são os seguintes:</p>
 
<h3>🦷 Administração de dados odontológicos</h3>
<p>✏️ Adicionar e modificar os dados dos dentistas.</p>
<p>📝 Registrar nome, sobrenome e matrícula de cadastro.</p>

<h3>🧑🏽‍🤝‍🧑🏽🧑‍🤝‍🧑 Administração de pacientes</h3>
<p>✏️ Registrar, modificar e excluir pacientes.</p> 
<p>💾 De cada um se armazenam: nome, sobrenome, endereço, RG, data de alta.</p>

<h3>📖 Registrar consulta</h3>
 <p>✏️ Deve ser possível permitir que um paciente seja
atribuído a uma consulta com um dentista em uma determinada data e
hora.</p>

<h3>👩🏽‍💻 Login</h3>
<p>🔒 Validar a entrada no sistema por meio de um login com nome de
usuário e senha.</p>
<p>🔑 Permitir que qualquer pessoa logada registre uma
consulta, mas apenas aqueles que têm uma função de administração pode
gerenciar dentistas e pacientes.</p>

<h2>🛠️ Requerimentos técnicos</h2>
<h3>A aplicação deve ser desenvolvida em camadas:</h3>
<p>✔️ Camada de entidade de negócios: são as classes Java do nosso negócio
modeladas através do paradigma orientado a objetos.</p>
<p>✔️ Camada de acesso a dados (Repositório): são as classes que se encarregam
de acessar o banco de dados.</p>
<p>✔️ Camada de dados (banco de dados): é o banco de dados do nosso sistema
modelado através de um modelo entidade-relacionamento. Usaremos a
base H2 por sua praticidade.</p>
<p>✔️ Camada de negócio: são as classes de serviço que se encarregam de
desacoplar o acesso aos dados da visão.</p>
<p>✔️ Camada de apresentação: estas são as telas da web que teremos que
desenvolver usando o framework Spring Boot MVC com os controladores e
uma dessas duas opções: HTML+JavaScript ou React para a visualização.</p>
