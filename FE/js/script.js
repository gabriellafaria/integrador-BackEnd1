const formulario = document.querySelector("form")
const dadosRua = document.querySelector(".rua");
const dadosNumero = document.querySelector(".numero");
const dadosCidade = document.querySelector(".cidade");
const dadosEstado = document.querySelector(".sigla_estado");
const dadoEstado = document.getElementById("estado");


function cadastrarEndereco(){
    fetch("http://localhost:8080/endereco",
    {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            rua: dadosRua.value,
            numero: dadosNumero.value,
            cidade: dadosCidade.value,
            estado: dadosEstado.value
            
        })

    })
    .then(function (res) { console.log(res)})
    .catch(function (res) { console.log(res)})
}

function limpar() {
    dadosRua.value = "";
    dadosNumero.value = "";
    dadosCidade.value = "";
    dadosEstado.value = "";
};

formulario.addEventListener('submit', function(event){
    event.preventDefault();

    cadastrarEndereco();
    
    limpar();
});

