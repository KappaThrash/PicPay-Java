# Infraestrutura e Implantação (Deployment) - PicPay

## Visão Geral

Este documento descreve as etapas necessárias para executar a aplicação PicPay localmente com Docker ou implantá-la na **AWS EC2** via **Terraform**.

---

## 1. Execução Local com Docker

### Pré-requisitos
* **Docker Engine** e **Docker Compose** instalados.
* **Java 21** e **Maven** (opcional para build local sem Docker).

### Variáveis de Ambiente (`.env`)
Antes de executar o container, configure o arquivo `.env` na raiz do projeto ou diretório `infra`:

```env
POSTGRES_ADDRESS=jdbc:postgresql://postgres:5432/picpay
POSTGRES_USER=picpay_user
POSTGRES_PASSWORD=picpay
RABBITMQ_ADDRESS=amqp://guest:guest@rabbitmq:5672
```

### Passo a Passo

1. **Subir os serviços dependentes (PostgreSQL 16 e RabbitMQ):**
   ```bash
   cd infra
   docker-compose up -d
   ```

2. **Gerar o arquivo JAR da aplicação:**
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. **Construir e rodar a imagem Docker da aplicação:**
   ```bash
   docker build -t picpay-app .
   docker run -d --name picpay-app --network picpay-network --env-file .env -p 8080:8080 picpay-app
   ```

---

## 2. Automação de Infraestrutura AWS com Terraform

O diretório `infra/` contém os arquivos de provisionamento para implantar o ambiente em uma instância **AWS EC2** em `us-east-1`.

### Recursos Provisionados
* **Security Group (`aws_security_group.security_Group`)**:
  * Porta `80` (HTTP pública)
  * Porta `22` (SSH)
  * Saída total (`egress 0.0.0.0/0`)
* **AWS Key Pair (`aws_key_pair.keypair`)**: Chave SSH pública (`~/.ssh/id_ed25519.pub`).
* **Instância EC2 (`aws_instance.server_Ec2`)**:
  * Tipo: `t3.micro`
  * AMI: `ami-00e801948462f718a` (Amazon Linux 2023)
  * Script `user_data.sh`: Instala Docker e Docker Compose automaticamente na inicialização.

### Provisionadores Remote-Exec / File
O Terraform executa os seguintes passos automaticamente após subir a instância:
1. Cria o diretório `/home/ec2-user/picpay`.
2. Envia os arquivos `docker-compose.yml`, `.env` e `start.sh` para o servidor via SSH.
3. Altera as permissões e executa o script `start.sh`, subindo os containers Docker do PostgreSQL, RabbitMQ e da aplicação (`danielkappa/picpay`).

### Executando o Terraform

```bash
cd infra

# Inicializar o Terraform
terraform init

# Visualizar o plano de execução
terraform plan

# Aplicar e criar a infraestrutura na AWS
terraform apply -auto-approve
```

---

## 3. Monitoramento e Diagnóstico

* **Logs da aplicação Docker:**
  ```bash
  docker logs -f picpay-app
  ```
* **Status dos serviços (PostgreSQL / RabbitMQ):**
  ```bash
  docker ps
  ```
