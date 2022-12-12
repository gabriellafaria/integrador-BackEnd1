const formulario = document.querySelector("form")
const nomePaciente = document.querySelector(".nome");
const sobrenomePaciente = document.querySelector(".sobrenome");
const rgPaciente = document.querySelector(".rg");
const ruaEndereco = document.querySelector(".rua");
const numeroEndereco = document.querySelector(".numero");
const cidadeEndereco = document.querySelector(".cidade");
const estadoEndereco = document.getElementById("estado");
const sendButton = document.getElementById("cadastrarButton");

const url = "http://localhost:8080/paciente";

function cadastrarPaciente(){
    const data = {
        nome: nomePaciente.value,
        sobrenome: sobrenomePaciente.value,
        rg: rgPaciente.value,
        endereco: {
            rua: ruaEndereco.value,
            numero: numeroEndereco.value,
            cidade: cidadeEndereco.value,
            estado: estadoEndereco.value
        }
    }
    fetch(url, {   
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)       
    })
    // .then(res => res.json())
    // .then(data => console.log(data))
     .catch(err => console.log(err))
}

sendButton.addEventListener('click', (event) => {
    cadastrarPaciente();
    limpar();
})

function limpar(){
    nomePaciente.value = "";
    sobrenomePaciente.value = "";
    rgPaciente.value = "";
    ruaEndereco.value = "";
    numeroEndereco.value = "";
    cidadeEndereco.value = "";
    estadoEndereco.value = "";
}

function buscarPacientes(){
    fetch(url, 
        {method: "GET"}
    )
    .then(res => res.json())
    .then(data => {
        var tbody = document.createElement('tbody');
        
        data.map(res => {
            let tr = document.createElement('tr');

            let nomeTd = document.createElement('td');
            let sobrenomeTd = document.createElement('td');
            let rgTd = document.createElement('td');
            let ruaTd = document.createElement('td');
            let numeroTd = document.createElement('td');
            let cidadeTd = document.createElement('td');
            let estadoTd = document.createElement('td');
            
            nomeTd.innerHTML = res.nome;
            sobrenomeTd.innerHTML = res.sobrenome;
            rgTd.innerHTML = res.rg;
            ruaTd.innerHTML = res.endereco.rua;
            numeroTd.innerHTML = res.endereco.numero;
            cidadeTd.innerHTML = res.endereco.cidade;
            estadoTd.innerHTML = res.endereco.estado;

            tr.appendChild(nomeTd);
            tr.appendChild(sobrenomeTd);
            tr.appendChild(rgTd);
            tr.appendChild(ruaTd);
            tr.appendChild(numeroTd);
            tr.appendChild(cidadeTd);
            tr.appendChild(estadoTd);

            tbody.appendChild(tr);
        })

        document.getElementById('pacienteTabela').appendChild(tbody);
        console.log(data);
    })
}

function buscarPorRg(){
    let rg = rgPaciente.value;
    console.log(rg)
    let urlRg = `http://localhost:8080/paciente/buscarRg/${rg}`;
    fetch(urlRg, 
        {method: "GET"}
    )
    .then(res => res.json())
    .then(res => {
        var tbody = document.createElement('tbody');
                
        let tr = document.createElement('tr');

        let nomeTd = document.createElement('td');
        let sobrenomeTd = document.createElement('td');
        let rgTd = document.createElement('td');
        let ruaTd = document.createElement('td');
        let numeroTd = document.createElement('td');
        let cidadeTd = document.createElement('td');
        let estadoTd = document.createElement('td');
        
        nomeTd.innerHTML = res.nome;
        sobrenomeTd.innerHTML = res.sobrenome;
        rgTd.innerHTML = res.rg;
        ruaTd.innerHTML = res.endereco.rua;
        numeroTd.innerHTML = res.endereco.numero;
        cidadeTd.innerHTML = res.endereco.cidade;
        estadoTd.innerHTML = res.endereco.estado;

        tr.appendChild(nomeTd);
        tr.appendChild(sobrenomeTd);
        tr.appendChild(rgTd);
        tr.appendChild(ruaTd);
        tr.appendChild(numeroTd);
        tr.appendChild(cidadeTd);
        tr.appendChild(estadoTd);

        tbody.appendChild(tr);
        
        document.getElementById('pacienteTabela').appendChild(tbody);
    })
}


const buscarButton = document.getElementById('buscar-paciente');

// buscarButton.addEventListener('click', (event) => {
//     buscarPacientes();
// })
