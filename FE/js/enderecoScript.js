const formulario = document.querySelector("form")
const idEndereco = document.querySelector(".id");
const ruaEndereco = document.querySelector(".rua");
const numeroEndereco = document.querySelector(".numero");
const cidadeEndereco = document.querySelector(".cidade");
const estadoEndereco = document.getElementById("estado");
const sendButton = document.getElementById("cadastrarButton");

console.log(idEndereco.value);


const url = "http://localhost:8080/endereco";

function buscarEnderecos(){
    fetch(url + "/buscar", 
        {method: "GET"}
    )
    .then(res => res.json())
    .then(data => {

        var tbody = document.createElement('tbody');
        
        data.map(res => {
            let tr = document.createElement('tr');

            let ruaTd = document.createElement('td');
            let numeroTd = document.createElement('td');
            let cidadeTd = document.createElement('td');
            let estadoTd = document.createElement('td');
            
            ruaTd.innerHTML = res.rua;
            numeroTd.innerHTML = res.numero;
            cidadeTd.innerHTML = res.cidade;
            estadoTd.innerHTML = res.estado;

            tr.appendChild(ruaTd);
            tr.appendChild(numeroTd);
            tr.appendChild(cidadeTd);
            tr.appendChild(estadoTd);

            tbody.appendChild(tr);
        })

        document.getElementById('enderecoTabela').appendChild(tbody);
    })
}

function buscarPorId(){
    let id = idEndereco.value;
    console.log(id)
    let urlId = `http://localhost:8080/endereco/?id=${id}`;
    fetch(urlId, 
        {method: "GET"}
    )
    .then(res => res.json())
    .then(res => {
        var tbody = document.createElement('tbody');
                
        let tr = document.createElement('tr');

        let ruaTd = document.createElement('td');
        let numeroTd = document.createElement('td');
        let cidadeTd = document.createElement('td');
        let estadoTd = document.createElement('td');
        


        ruaTd.innerHTML = res.rua;
        numeroTd.innerHTML = res.numero;
        cidadeTd.innerHTML = res.cidade;
        estadoTd.innerHTML = res.estado;


  
        tr.appendChild(ruaTd);
        tr.appendChild(numeroTd);
        tr.appendChild(cidadeTd);
        tr.appendChild(estadoTd);

        tbody.appendChild(tr);
        
        document.getElementById('enderecoTabela').appendChild(tbody);
    })
}


const buscarButton = document.getElementById('buscar-endereco');
