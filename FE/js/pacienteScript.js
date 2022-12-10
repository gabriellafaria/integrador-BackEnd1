const formulario = document.querySelector("form")
const nomePaciente = document.querySelector(".nome");
const sobrenomePaciente = document.querySelector(".sobrenome");
const rgPaciente = document.querySelector(".rg");
const ruaEndereco = document.querySelector(".rua");
const numeroEndereco = document.querySelector(".numero");
const cidadeEndereco = document.querySelector(".cidade");
const estadoEndereco = document.getElementById("estado");

function cadastrarPaciente(){
    fetch("http://localhost:8080/paciente",
    {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            nome: nomePaciente.value,
            sobrenome: sobrenomePaciente.value,
            rg: rgPaciente.value,
            endereco: {
                rua: ruaEndereco.value,
                numero: numeroEndereco.value,
                cidade: cidadeEndereco.value,
                estado: estadoEndereco.value
            }
        })       
    })
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((err) => console.log(err))
}