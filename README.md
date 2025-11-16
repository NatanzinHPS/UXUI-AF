# UXUI-AF

## Atividade Individual: Caixa Branca

| ID | Item                                       | Status | Artefato com erro                                                                                         | Correções a serem realizadas                                                                                                                             |
|----|--------------------------------------------|--------|----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | O código foi devidamente documentado? | NÃO    | Classe User<br> Método conectarBD()<br> Método verificarUsuario()                                        | Faltou documentar a classe e os métodos com Javadoc.<br> - Javadoc na classe descrevendo responsabilidade<br> - Javadoc em conectarBD() com @return e @throws<br> - Javadoc em verificarUsuario() com @param, @return e descrição |
| 2  | As variáveis e constantes possuem nomenclatura adequada? | PARCIAL| String url hardcoded                                   | Criar constantes DB_URL, DB_USER, DB_PASSWORD |
| 3  | Existem legibilidade e organização no código? | NÃO    | Blocos catch vazios<br> Variáveis públicas<br> Credenciais hardcoded              | Múltiplos problemas de organização:<br> - Implementar tratamento nos blocos catch (exceção)<br> - Tornar variáveis private e criar getters/setter<br> - Mover credenciais para arquivo de configuração externo |
| 4  | Todos os NullPointers foram tratados? | NÃO    | Connection conn<br> Parâmetros login e senha<br> ResultSet rs                                           | Nenhuma validação de null implementada:<br> - Validar conn != null antes de usar em verificarUsuario()<br> - Validar login e senha != null no início do método<br> - Verificar rs != null antes de chamar next() e getString() |
| 5  | As conexões utilizadas foram fechadas? | NÃO    | Connection conn<br> Statement st<br> ResultSet rs                                                      | Implementar try-with-resources ou fechar no finally<br> - Fechar Connection, Statement e ResultSet |

---

## Fluxograma

<img width="1366" height="2918" alt="Diagrama em branco" src="https://github.com/user-attachments/assets/7bc908f2-84fc-4e89-8859-2567ebf24444" />

---

## Grafo de fluxo

<img width="3474" height="1266" alt="Diagrama em branco (4)" src="https://github.com/user-attachments/assets/22d98f79-4135-4bca-8490-ba590157c88a" />

### **Descrição dos Nós**

N1  - Início do método verificarUsuario()  
N2  - Validação de parâmetros (login e senha não nulos/vazios)  
N3  - Montagem da query SQL  
N4  - Chamada do método conectarBD()  
N5  - Try-Catch: Class.forName() e getConnection()  
N6  - Conexão estabelecida com sucesso  
N7  - Tratamento de exceção na conexão  
N8  - Retorno da conexão (conn)  
N9  - Try-Catch: Execução da query SQL  
N10 - Decisão: if (rs.next()) → existe resultado?  
N11 - Atribuição: result = true e nome = rs.getString("nome")  
N12 - Tratamento de exceção SQL  
N13 - Fim: retorno de result (boolean)

---

## Complexidade ciclomática

E: 15 arestas<br>
N: 13 nós<br>
P: 1 componente conectado<br>
M = E − N + 2P<br>
M = 15 - 13 + 2.1<br>
M = 4<br>

V(G) = 3(Nós de decisão) + 1<br>
V(G) = 4

---

## Caminhos básicos

### Caminho 1: Sucesso Total<br>
N1 → N2 → N3 → N4 → N5 → N6 → N8 → N9 → N10 → N11 → N13 → FIM<br>
Conexão OK + SQL OK + Usuário encontrado

### Caminho 2: Falha na Conexão + SQL Error<br>
N1 → N2 → N3 → N4 → N5 → N7 → N8 → N9 → N12 → N13 → FIM<br>
Conexão falha + SQL falha

### Caminho 3: Conexão OK mas Usuário Não Encontrado<br>
N1 → N2 → N3 → N4 → N5 → N6 → N8 → N9 → N10 → N13 → FIM<br>
Conexão OK + SQL OK + Sem usuário

### Caminho 4: Conexão OK mas SQL Error<br>
N1 → N2 → N3 → N4 → N5 → N6 → N8 → N9 → N12 → N13 → FIM<br>
Conexão OK + SQL falha

---

## Teste do codigo refatorado

<img width="1855" height="1080" alt="image" src="https://github.com/user-attachments/assets/164f8772-1614-47b8-a89d-16763eff55d0" />
