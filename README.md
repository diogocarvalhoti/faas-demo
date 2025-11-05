# 🚀 Serverless na Prática: Desbravando o Mundo do FaaS

**Autor:** Diogo Carvalho de Matos – Arquiteto de Software e Soluções  
**Tema:** Serverless na Prática: Desbravando o Mundo do FaaS  

---

## 🧭 Agenda

### 📐 **PARTE 1: Visão de Arquitetura/Infraestrutura**

1. **Introdução ao FaaS** - Conceitos fundamentais
2. **Problemas que o FaaS resolve** - Benefícios e casos de uso
3. **Como os provedores implementam FaaS** - Arquiteturas e diferenças
4. **Knative: Serverless para Kubernetes** - Componentes e funcionamento
5. **Configuração do Knative em cluster on-premise** - Setup passo a passo
6. **Arquitetura e escalabilidade** - Como funciona internamente

### 👨‍💻 **PARTE 2: Visão do Desenvolvedor** 

7. **Criando uma função Spring Boot** - Desenvolvimento prático
8. **Build e Deploy** - Do código à produção
9. **Testando a função** - Validação e execução
10. **Serverless em ação** - Escalabilidade automática e scale-to-zero
11. **Conclusões e próximos passos**

---

## 📐 PARTE 1: VISÃO DE ARQUITETURA/INFRAESTRUTURA

> **Foco:** Como o FaaS funciona, configuração e operação do Knative no Kubernetes

---

## ⚙️ O que é Function as a Service (FaaS)

**Function as a Service (FaaS)** é um modelo de computação serverless onde:

- 🎯 **Execução orientada a eventos** - Funções são invocadas por eventos específicos
- ⚡ **Sem gerenciamento de infraestrutura** - Plataforma abstrai completamente os servidores
- 💰 **Modelo de cobrança por uso** - Pague apenas pelo tempo de execução e número de invocações
- 🔄 **Auto-scaling** - Escala automaticamente de 0 até milhares de instâncias
- 🚀 **Foco no código** - Desenvolvedor escreve apenas a lógica de negócio

### Características Principais

- **Cold Start vs Warm Start**: Primeira execução pode ter latência maior
- **Stateless**: Funções não mantêm estado entre execuções
- **Timeouts**: Limites de tempo de execução (geralmente 5-15 minutos)
- **Tamanho do código**: Limitações de memória e tamanho do pacote

### Casos de Uso Ideais

✅ Processamento de imagens/vídeos sob demanda  
✅ APIs REST leves e rápidas  
✅ Processamento de eventos (filas, streams)  
✅ Webhooks e integrações  
✅ Transformação de dados  
✅ Tarefas agendadas (cron jobs)

📊 **Exemplo Prático:**  
Um serviço que consulte a base/api de endereços **apenas quando necessário** — sem manter servidores ativos 24/7, economizando recursos e custos.

---

## 💡 Problemas que o FaaS Resolve

### Antes do FaaS (Modelo Tradicional)

❌ **Provisão e gerenciamento de servidores** - Time gasto com infraestrutura  
❌ **Ociosidade de recursos** - Servidores ativos mesmo sem requisições  
❌ **Custos fixos altos** - Pagamento contínuo independente de uso  
❌ **Escalabilidade manual** - Necessário planejamento e intervenção humana  
❌ **Tempo de deploy lento** - Provisionamento de infra leva tempo  
❌ **Complexidade operacional** - Manutenção, patches, segurança

### Com FaaS

✅ **Zero infraestrutura** - Foco total no código de negócio  
✅ **Economia de custos** - Pague apenas pelo que usar  
✅ **Escalabilidade automática** - De 0 a milhares de instâncias em segundos  
✅ **Deploy rápido** - Minutos do código à produção  
✅ **Operações simplificadas** - Plataforma gerencia tudo automaticamente  
✅ **Alta disponibilidade** - Redundância e failover automáticos


---

## ☁️ Como os Provedores de Cloud Implementam FaaS

### AWS Lambda

**Arquitetura:**
- Runtime baseado em containers (Firecracker microVMs)
- Layers para dependências compartilhadas
- Integration com 200+ serviços AWS
- Suporte a 15+ linguagens (incluindo Java, Python, Node.js, Go, .NET)

**Características:**
- Cold start: ~100ms-3s (dependendo da runtime)
- Memory: 128 MB a 10 GB
- Timeout: 15 minutos
- Concorrência: até 1000 execuções simultâneas por função

**Quando usar:**
- Ecossistema AWS completo
- Integração nativa com S3, DynamoDB, API Gateway
- Necessidade de VPC integration

---

### Azure Functions

**Arquitetura:**
- Runtime baseado em .NET Core (open source)
- Hosting plans: Consumption, Premium, Dedicated
- Integração com Logic Apps e Event Grid
- Durable Functions para workflows complexos

**Características:**
- Cold start: ~200ms-2s
- Memory: 128 MB a 3.5 GB (Consumption plan)
- Timeout: 10 minutos (Consumption), ilimitado (Premium)
- Suporte a triggers HTTP, Timer, Queue, Blob, Event Hubs

**Quando usar:**
- Está no ecossistema Microsoft/Azure
- Precisa de integração com Logic Apps
- Desenvolvimento em .NET/C#

---

### Google Cloud Functions

**Arquitetura:**
- Runtime baseado em containers (Cloud Run internamente)
- Geração 2 com Cloud Run (melhor performance)
- Integração com Firebase, Cloud Pub/Sub
- Buildpacks para deployment

**Características:**
- Cold start: ~100ms-1s (Gen 2)
- Memory: 128 MB a 8 GB
- Timeout: 60 minutos (Gen 2)
- Escala até 1000 instâncias por função

**Quando usar:**
- Está no ecossistema Google Cloud
- Precisa de baixa latência e cold start rápido
- Integração com Firebase

---

### Comparação: Cloud vs On-Premise

| Aspecto | Cloud (AWS/Azure/GCP) | On-Premise (Knative) |
|---------|----------------------|----------------------|
| **Setup** | Configuração imediata | Requer cluster Kubernetes |
| **Custo** | Pay-per-use | Infraestrutura própria |
| **Portabilidade** | Vendor lock-in | Multi-cloud, portável |
| **Customização** | Limitada | Total controle |
| **Compliance** | Dependente do provider | Controle total dos dados |
| **Cold Start** | Otimizado pelo provider | Depende da configuração |

---

## 🧠 Knative: Serverless para Kubernetes

### O que é Knative?

**Knative** é uma plataforma open-source da **CNCF (Cloud Native Computing Foundation)** que adiciona componentes serverless ao Kubernetes, permitindo:

- 🚀 Deploy de funções e aplicações serverless
- 📈 Auto-scaling baseado em métricas (incluindo scale-to-zero)
- 🔄 Gerenciamento de tráfego e versões
- 📡 Sistema de eventos para arquitetura event-driven

### Por que Knative?

✅ **Open Source** - Sem vendor lock-in  
✅ **Portabilidade** - Roda em qualquer Kubernetes (cloud ou on-premise)  
✅ **Padrão da indústria** - Mantido pela CNCF, usado por grandes empresas  
✅ **Flexibilidade** - Controle total sobre a infraestrutura  
✅ **Integração nativa** - Funciona com ferramentas Kubernetes existentes

### Componentes Principais

#### 1. **Knative Serving** (Core do Serverless)

Gerencia o ciclo de vida das aplicações serverless:

- **Knative Service (KSVC)**: Recurso principal que gerencia o deploy
- **Configuration**: Versões da aplicação
- **Revision**: Snapshot imutável de uma configuração
- **Route**: Roteamento de tráfego entre revisões
- **Autoscaler**: Escala baseado em métricas (concorrência, requisições)

**Fluxo de Funcionamento:**
```
Request → Kourier Ingress → Knative Service → Revision → Pods
                                                      ↓
                                            Autoscaler (scale 0-N)
```

#### 2. **Knative Eventing** (Event-Driven)

Permite funções reagirem a eventos de múltiplas fontes:

- **Sources**: Origem dos eventos (Kafka, GitHub, S3, etc.)
- **Brokers/Channels**: Camada de mensageria
- **Triggers**: Conecta eventos a funções
- **CloudEvents**: Padrão CNCF para eventos

**Casos de uso:**
- Webhooks
- Integração com filas (Kafka, RabbitMQ)
- Processamento assíncrono
- Arquitetura event-driven

### Arquitetura Knative no Kubernetes

```text
┌─────────────────────────────────────────────────────────┐
│                    Kubernetes Cluster                   │
│                                                         │
│  ┌──────────────────────────────────────────────────┐   │
│  │           Knative Control Plane                  │   │
│  │  ┌──────────────┐         ┌──────────────┐       │   │
│  │  │   Serving    │────────▶│   Eventing   │       │   │
│  │  │  Controller  │         │  Controller  │       │   │
│  │  └──────────────┘         └──────────────┘       │   │
│  └──────────────────────────────────────────────────┘   │
│                           │                             │
│                           ▼                             │
│  ┌──────────────────────────────────────────────────┐   │
│  │            Knative Services (Pods)               │   │
│  │  ┌──────────┐  ┌──────────┐  ┌──────────┐        │   │
│  │  │ Function │  │ Function │  │ Function │        │   │
│  │  │   Pod    │  │   Pod    │  │   Pod    │        │   │
│  │  └──────────┘  └──────────┘  └──────────┘        │   │
│  └──────────────────────────────────────────────────┘   │
│                           │                             │
│                           ▼                             │
│  ┌──────────────────────────────────────────────────┐   │
│  │         Kourier Ingress (Load Balancer)          │   │
│  └──────────────────────────────────────────────────┘   │
│                           │                             │
└───────────────────────────┼─────────────────────────────┘
                            │
                            ▼
                    ┌───────────────┐
                    │   Cliente     │
                    └───────────────┘
```

### Escalabilidade no Knative

**Scale-to-Zero:**
- Após período de inatividade (default: 30s), pods são removidos
- Primeira requisição após inatividade aciona "cold start"
- Reduz custos drasticamente

**Scale-Up:**
- Baseado em métricas de concorrência e requisições por segundo
- Pode escalar de 0 a N pods em segundos
- Configurável via annotations

**Exemplo de configuração:**
```yaml
annotations:
  autoscaling.knative.dev/minScale: "0"  # Permite scale-to-zero
  autoscaling.knative.dev/maxScale: "10" # Limite máximo
  autoscaling.knative.dev/target: "10"   # Requisições por pod (configurado para demonstração)
```

---

## 🧰 Configuração do Knative em Cluster On-Premise

> **Perspectiva de Infraestrutura:** Passo a passo completo para instalar e configurar o Knative

### Pré-requisitos

Antes de instalar o Knative, certifique-se de ter:

✅ **Kubernetes cluster** funcionando (v1.25+)  
✅ **kubectl** configurado e com acesso ao cluster  
✅ **Istio** ou **Kourier** para ingress (usaremos Kourier)  
✅ **Métricas** (Metrics Server) para autoscaling  
✅ **DNS** configurado ou usar nip.io para desenvolvimento

### Passo 1: Verificar o Cluster Kubernetes

```bash
# Verificar versão do cluster
kubectl version --short

# Verificar nós do cluster
kubectl get nodes

# Verificar se o Metrics Server está instalado (necessário para autoscaling)
kubectl top nodes
```

### Passo 2: Instalar Knative Serving CRDs

**Custom Resource Definitions** definem os recursos personalizados do Knative:

```bash
kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.19.7/serving-crds.yaml
```

**O que isso instala:**
- `services.serving.knative.dev` - Knative Services
- `configurations.serving.knative.dev` - Configurations
- `revisions.serving.knative.dev` - Revisions
- `routes.serving.knative.dev` - Routes

**Verificar instalação:**
```bash
kubectl get crd | grep knative
```

### Passo 3: Instalar Knative Serving Core

Instala os controladores que gerenciam os recursos Knative:

```bash
kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.19.7/serving-core.yaml
```

**Verificar se os pods estão rodando:**
```bash
kubectl get pods -n knative-serving
# Deve mostrar pods: controller, autoscaler, networking-istio, webhook
```

### Passo 4: Instalar Kourier (Ingress Controller)

**Kourier** é um ingress controller leve para Knative (alternativa ao Istio):

```bash
kubectl apply -f https://github.com/knative-extensions/net-kourier/releases/download/knative-v1.19.6/kourier.yaml
```

**Verificar instalação:**
```bash
kubectl get pods -n kourier-system
# Deve mostrar: 3scale-kourier-control e 3scale-kourier-gateway
```

### Passo 5: Configurar Domínio Padrão

Para desenvolvimento local, vamos usar **nip.io** que resolve IPs automaticamente:

```bash
# Instalar configuração de domínio padrão
kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.19.7/serving-default-domain.yaml

# Configurar domínio local (127.0.0.1.nip.io)
kubectl patch configmap/config-domain \
  --namespace knative-serving \
  --type merge \
  --patch '{"data": {"127.0.0.1.nip.io": ""}}'
```

**Para produção:** Configure seu próprio domínio DNS apontando para o IP do ingress.

### Passo 6: Configurar Kourier como Ingress Padrão

```bash
kubectl patch configmap/config-network \
  --namespace knative-serving \
  --type merge \
  --patch '{"data":{"ingress-class":"kourier.ingress.networking.knative.dev"}}'
```

### Passo 7: Instalar HPA (Horizontal Pod Autoscaler)

HPA permite autoscaling baseado em CPU/memória:

```bash
kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.19.7/serving-hpa.yaml
```

### Passo 8: Verificar Instalação Completa

```bash
# Verificar todos os componentes
kubectl get pods --all-namespaces | grep -E "knative|kourier"

# Verificar se Knative está pronto
kubectl get knative
```

### Passo 9: Configurar Port Forward (Desenvolvimento Local)

Para acessar serviços localmente sem configurar LoadBalancer:

```bash
# Em um terminal separado, fazer port-forward do Kourier
kubectl port-forward --namespace kourier-system service/kourier 8080:80
```

Agora você pode acessar serviços em: `http://<servico>.<namespace>.127.0.0.1.nip.io:8080`

### Resumo da Instalação

Após completar todos os passos, você terá:

✅ **Knative Serving** instalado e configurado  
✅ **Kourier** como ingress controller  
✅ **HPA** para autoscaling baseado em métricas  
✅ **Domínio** configurado para desenvolvimento  
✅ **Cluster** pronto para receber funções serverless

---

## 🏗️ Arquitetura e Escalabilidade: Como Funciona Internamente

### Fluxo de Requisição no Knative

```text
1. Requisição HTTP
   ↓
2. Kourier Ingress (Load Balancer)
   ↓
3. Knative Route (Roteamento)
   ↓
4. Revision Check (Verifica se existe pod ativo)
   ↓
5a. Se pod existe → Roteia para o pod
5b. Se não existe → Aciona Autoscaler → Cria pod → Cold Start
   ↓
6. Função executa
   ↓
7. Resposta retornada
```

### Componentes de Autoscaling

#### KPA (Knative Pod Autoscaler)
- Métrica principal: **Concorrência** (requisições simultâneas por pod)
- Target default: 100 requisições concorrentes por pod
- Reativa: Escala baseado em requisições ativas

#### HPA (Horizontal Pod Autoscaler) - Alternativa
- Métrica principal: **CPU/Memória**
- Configurável via annotations
- Mais apropriado para workloads CPU-intensive

### Como o Scale-to-Zero Funciona

1. **Período de Inatividade**: Após 30 segundos sem requisições
2. **Queue Proxy**: Mantém um "queue-proxy" que intercepta requisições
3. **Activator**: Primeira requisição após inatividade vai para o Activator
4. **Wake-up**: Activator acorda um pod (ou cria novo)
5. **Request Buffering**: Requisições são bufferizadas durante cold start

### Métricas Coletadas

O Knative coleta métricas através do **Queue Proxy** (sidecar em cada pod):

- **Concurrency**: Requisições simultâneas
- **RPS** (Requests Per Second): Taxa de requisições
- **Latency**: Tempo de resposta (p50, p90, p99)
- **Pod Count**: Número de pods ativos

### Configurações Importantes

```yaml
# Configuração Global (ConfigMap)
apiVersion: v1
kind: ConfigMap
metadata:
  name: config-autoscaler
  namespace: knative-serving
data:
  scale-to-zero-grace-period: "30s"        # Tempo antes de scale-to-zero
  stable-window: "60s"                      # Janela para estabilizar métricas
  panic-window-percentage: "10.0"           # Janela de pânico (scale rápido)
  max-scale-up-rate: "1000.0"               # Máxima taxa de scale-up
```

### Observabilidade na Camada de Infraestrutura

**Monitorar recursos Knative:**

```bash
# Ver status de todos os serviços
kubectl get ksvc --all-namespaces

# Ver revisões e seu tráfego
kubectl get revisions -n cep

# Ver configurações de autoscaling
kubectl get podautoscaler -n cep

# Ver métricas do autoscaler
kubectl logs -n knative-serving -l app=autoscaler --tail=100

# Ver eventos do Knative
kubectl get events -n cep --sort-by='.lastTimestamp'
```

**Componentes para monitorar:**

- `controller`: Gerencia o ciclo de vida dos recursos
- `autoscaler`: Calcula e aplica scaling decisions
- `activator`: Gerencia cold starts e scale-to-zero
- `webhook`: Validação e mutação de recursos
- `queue-proxy`: Sidecar que coleta métricas em cada pod

---

## 🔄 Transição: Da Infraestrutura ao Desenvolvimento

Agora que entendemos **como o FaaS funciona** e **como configurar o Knative** no Kubernetes, vamos mudar para a **perspectiva do desenvolvedor**:

- ✅ Infraestrutura configurada e pronta
- ✅ Knative instalado e funcionando
- ✅ Cluster Kubernetes operacional

**Próximo passo:** Criar e deployar uma função real!

---

## 👨‍💻 PARTE 2: VISÃO DO DESENVOLVEDOR

> **Foco:** Criar funções, realizar deploy e observar o serverless em funcionamento

**O que vamos fazer:**
1. Criar uma função Spring Boot usando templates
2. Implementar a lógica de negócio
3. Fazer build e deploy da função
4. Testar e validar o funcionamento
5. Observar escalabilidade automática em ação

---

## 🧱 Arquitetura da Demonstração

```text
┌─────────────────────────────────────────────────────────────┐
│                    Desenvolvedor/CI/CD                      │
└────────────┬────────────────────────────────────────────────┘
             │
             │ 1. Código Spring Boot Function
             ▼
┌─────────────────────────────────────────────────────────────┐
│              Spring Cloud Function Application              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  @Bean Function<Message<String>, String> cep()       │   │
│  │  - Recebe CEP via HTTP POST                          │   │
│  │  - Consulta API ViaCEP                               │   │
│  │  - Retorna JSON com dados do endereço                │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────┬────────────────────────────────────────────────┘
             │
             │ 2. Build (Maven + Docker)
             ▼
┌─────────────────────────────────────────────────────────────┐
│              Container Image (Docker Hub)                   │
│              diogocarvalho/faas-demo:latest                 │
└────────────┬────────────────────────────────────────────────┘
             │
             │ 3. Deploy (kn CLI ou kubectl)
             ▼
┌─────────────────────────────────────────────────────────────┐
│              Kubernetes Cluster (On-Premise)                │
│                                                             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │          Knative Service (KSVC)                      │   │
│  │  ┌──────────────────────────────────────────────┐    │   │
│  │  │  Configuration → Revision → Deployment       │    │   │
│  │  └──────────────────────────────────────────────┘    │   │
│  │                      │                               │   │
│  │                      ▼                               │   │
│  │  ┌──────────────────────────────────────────────┐    │   │
│  │  │  Autoscaler (KPA/HPA)                        │    │   │
│  │  │  - Scale: 0 → N pods                         │    │   │
│  │  │  - Baseado em concorrência/requisições       │    │   │
│  │  └──────────────────────────────────────────────┘    │   │
│  └──────────────────────────────────────────────────────┘   │
│                      │                                      │
│                      ▼                                      │
│  ┌──────────────────────────────────────────────────────┐   │
│  │           Kourier Ingress Controller                 │   │
│  │  - Exposição HTTP/HTTPS                              │   │
│  │  - Load Balancing                                    │   │
│  │  - Domain: api.cep.127.0.0.1.nip.io                  │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────┬────────────────────────────────────────────────┘
             │
             │ 4. HTTP Request
             ▼
┌─────────────────────────────────────────────────────────────┐
│                    Cliente (curl/browser)                   │
└─────────────────────────────────────────────────────────────┘
```

---

## ☕ Criando a Função Spring Boot

### Pré-requisitos para Desenvolvimento

```bash
# Instalar Knative Func CLI
# macOS
brew install knative-sandbox/kn-plugins/func

# Linux
wget https://github.com/knative/func/releases/latest/download/func_linux_amd64
chmod +x func_linux_amd64
sudo mv func_linux_amd64 /usr/local/bin/func

# Verificar instalação
func version
```

### Passo 1: Criar Projeto da Função

O Knative Func CLI oferece templates pré-configurados:

```bash
# Verificar templates disponíveis
kn func create --help

# Criar função Spring Boot
kn func create -l springboot cep-function-springboot

# Navegar para o diretório
cd cep-function-springboot
```

**Templates disponíveis:**
- `http`: Função exposta via HTTP (ideal para APIs REST)
- `cloudevents`: Função que processa eventos CloudEvents
- `springboot`: Template completo com Spring Boot e Spring Cloud Function

### Passo 2: Estrutura do Projeto

Após criar, você terá:

```
cep-function-springboot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── functions/
│   │   │       └── CloudFunctionApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── func.yaml          # Configuração do Knative Func
└── Dockerfile         # Opcional (gerado automaticamente)
```

### Passo 3: Implementar a Lógica da Função

Edite `src/main/java/functions/CloudFunctionApplication.java`:

```java
package functions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@SpringBootApplication
public class CloudFunctionApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudFunctionApplication.class, args);
  }

  @Bean
  public Function<Message<String>, String> cep() {
    return (inputMessage) -> {
      String cep = inputMessage.getPayload();
      
      // Validação básica do CEP (8 dígitos)
      if (cep == null || !cep.matches("\\d{8}")) {
        return "{\"erro\": \"CEP inválido. Use 8 dígitos.\"}";
      }
      
      // Consulta API ViaCEP
      String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";
      RestTemplate restTemplate = new RestTemplate();
      
      try {
        String resultado = restTemplate.getForObject(apiUrl, String.class);
        
        // Verifica se o CEP foi encontrado
        if (resultado != null && resultado.contains("\"erro\"")) {
          return "{\"erro\": \"CEP não encontrado.\"}";
        }
        
        return resultado;
      } catch (Exception e) {
        return "{\"erro\": \"Erro ao consultar API: " + e.getMessage() + "\"}";
      }
    };
  }
}
```

**Explicação:**
- `@SpringBootApplication`: Configuração automática do Spring Boot
- `Function<Message<String>, String>`: Interface funcional do Spring Cloud Function
- `Message<String>`: Permite acesso a headers e payload
- A função será exposta automaticamente como endpoint HTTP `/cep`

### Passo 4: Configurar func.yaml

O arquivo `func.yaml` define como a função será construída e deployada:

```yaml
specVersion: 0.36.0
name: cep
runtime: springboot
registry: diogocarvalho/faas-demo  # Seu registry Docker
image: index.docker.io/diogocarvalho/faas-demo/cep:latest
build:
  builder: pack
  buildEnvs:
    - name: BP_NATIVE_IMAGE
      value: "false"  # true para GraalVM native image
    - name: BP_JVM_VERSION
      value: "21"
deploy:
  healthEndpoints:
    liveness: /actuator/health
    readiness: /actuator/health
```

### Passo 5: Testar Localmente

```bash
# Executar localmente (requer Docker)
func run

# Em outro terminal, testar
curl -X POST http://localhost:8080/cep \
  -H "Content-Type: text/plain" \
  -d "71691000"
```

---

## 🐳 Build da Imagem Docker

### Opção 1: Usando Dockerfile Manual

Se preferir controle total, crie um `Dockerfile`:

```dockerfile
FROM --platform=linux/arm64 alpine:3.19
# FROM --platform=linux/amd64 alpine:3.19

# Instalar bibliotecas necessárias para binários nativos
RUN apk add --no-cache libc6-compat zlib

# Copiar o binário nativo (já buildado localmente para Linux)
COPY target/function /app/function

# Tornar executável
RUN chmod +x /app/function

# Expor porta padrão do Spring Boot
EXPOSE 8080

# Usar usuário não-root (segurança)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Executar o binário nativo
ENTRYPOINT ["/app/function"]

```

**Build e push manual:**

Por tratar-se de um ambiente local MacOS ARM64, temos que buildar a imagem para linux, com isso, criei o script shell abaixo para facilitar este procedimento:

```bash
#!/bin/bash

# Script para executar Maven dentro do Docker (como se fosse local)
# Permite usar comandos Maven normalmente, mas roda dentro do container

set -e

# Verificar se Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Por favor, inicie o Docker e tente novamente."
    exit 1
fi

# Diretório do projeto
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Detectar plataforma
PLATFORM="${DOCKER_PLATFORM:-linux/arm64}"

# Executar Maven dentro do container Docker
# Monta o diretório do projeto como volume, então as mudanças são preservadas
docker run --rm -it \
  --platform "$PLATFORM" \
  -v "$PROJECT_DIR:/build" \
  -w /build \
  -v "$HOME/.m2:/root/.m2" \
  ghcr.io/graalvm/graalvm-community:latest \
  bash -c "
    # Instalar native-image se não estiver instalado
    if ! command -v native-image &> /dev/null; then
      echo '📦 Instalando native-image...'
      gu install native-image
    fi
    
    # Executar o comando Maven passado como argumento
    ./mvnw \"\$@\"
  " -- "$@"
```

```bash
# Compilar projeto
./build.sh -P native native:compile -DskipTests

#Build e Push da imagem
docker build -t diogocarvalho/faas-demo:latest . && docker push diogocarvalho/faas-demo:latest
```

### Opção 2: Usando Knative Func CLI (Recomendado)

O CLI gerencia build e push automaticamente:

```bash
# Configurar registry (se ainda não fez)
export FUNC_REGISTRY=docker.io/diogocarvalho/faas-demo

# Build usando Cloud Native Buildpacks (pack)
func build

# Ou build + push + deploy em um comando
func deploy --registry docker.io/diogocarvalho/faas-demo
```

---

## 🏗️ Deploy no Cluster Knative

### Método 1: Usando Knative Func CLI (Mais Simples)

```bash
# Deploy direto (faz build + push + deploy)
func deploy \
  --registry docker.io/diogocarvalho/faas-demo \
  --namespace cep \
  --verbose

# Ou se já configurou FUNC_REGISTRY
func deploy --namespace cep
```

### Método 2: Usando kn CLI (Knative CLI)

```bash
# Criar namespace (se não existir)
kubectl create namespace cep

# Deploy do serviço
kn service create api \
  --image docker.io/diogocarvalho/faas-demo:latest \
  --port 8080 \
  --namespace cep \
  --env JAVA_OPTS="-Xmx512m" \
  --annotation autoscaling.knative.dev/minScale="0" \
  --annotation autoscaling.knative.dev/maxScale="10" \
  --annotation autoscaling.knative.dev/target="10"

# Ou usar arquivo de configuração
kn service apply -f service.yaml
```

### Método 3: Usando kubectl (YAML)

Criar arquivo `service.yaml`:

```yaml
apiVersion: serving.knative.dev/v1
kind: Service
metadata:
  name: api
  namespace: cep
spec:
  template:
    metadata:
      annotations:
        autoscaling.knative.dev/minScale: "0"
        autoscaling.knative.dev/maxScale: "10"
        autoscaling.knative.dev/target: "10"
    spec:
      containers:
      - image: docker.io/diogocarvalho/faas-demo:latest
        ports:
        - containerPort: 8080
        env:
        - name: JAVA_OPTS
          value: "-Xmx512m"
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
```

Aplicar:
```bash
kubectl apply -f service.yaml
```

### Verificar Deploy

```bash
# Verificar status do serviço
kn service list -n cep

# Ver detalhes
kn service describe api -n cep

# Ver URL do serviço
kn service list -n cep -o url

# Ver pods (inicialmente pode estar 0 - scale-to-zero)
kubectl get pods -n cep -w
```

---

## 🧪 Testando a Função

### Obter URL do Serviço

```bash
# Usando kn CLI
kn service describe api -n cep | grep URL

# Ou verificar via kubectl
kubectl get ksvc api -n cep -o jsonpath='{.status.url}'
```

**Exemplo de URL:** `http://api.cep.127.0.0.1.nip.io`

### Testar a Função

```bash
# Teste básico
curl -X POST \
  http://api.cep.127.0.0.1.nip.io \
  -H "Content-Type: text/plain" \
  -d "71691058"

# Com port-forward (se necessário)
kubectl port-forward -n kourier-system service/kourier 8080:80

curl -X POST \
  http://api.cep.127.0.0.1.nip.io:8080 \
  -H "Content-Type: text/plain" \
  -d "71691058"
```

**Resposta esperada:**
```json
{
  "cep": "71691-058",
  "logradouro": "SQN 107 Bloco H",
  "complemento": "",
  "bairro": "Asa Norte",
  "localidade": "Brasília",
  "uf": "DF",
  "ibge": "5300108",
  "gia": "",
  "ddd": "61",
  "siafi": "9701"
}
```

### Testar Cenários

```bash
# CEP válido
curl -X POST http://api.cep.127.0.0.1.nip.io \
  -H "Content-Type: text/plain" \
  -d "01310100"

# CEP inválido
curl -X POST http://api.cep.127.0.0.1.nip.io \
  -H "Content-Type: text/plain" \
  -d "123"

# CEP inexistente
curl -X POST http://api.cep.127.0.0.1.nip.io \
  -H "Content-Type: text/plain" \
  -d "00000000"
```

---

## 📈 Serverless em Ação: Observando o Comportamento na Prática

> **Perspectiva do Desenvolvedor:** Ver na prática como a função escala automaticamente

### Demonstração 1: Observando o Scale-to-Zero

**Objetivo:** Ver como a função escala para zero após inatividade

**Passo 1: Verificar estado inicial (zero pods)**

Em um terminal, monitore os pods:

```bash
# Terminal 1: Monitorar pods em tempo real
watch -n 1 'kubectl get pods -n cep'
```

**Passo 2: Fazer primeira requisição (Cold Start)**

Em outro terminal, faça uma requisição e meça o tempo:

```bash
# Terminal 2: Requisição que vai acionar o cold start
echo "=== Primeira requisição (Cold Start) ==="
time curl -X POST http://api.cep.127.0.0.1.nip.io:8080 \
  -H "Content-Type: text/plain" \
  -d "71691058"
```

**O que observar:**
- ⏱️ **Tempo de resposta:** Pode levar 15-30 segundos na primeira requisição
- 📦 **Pod sendo criado:** No Terminal 1, você verá o pod aparecer
- 🔄 **Status do pod:** Iniciando como "Pending" → "ContainerCreating" → "Running"

**Passo 3: Fazer requisições subsequentes (Warm)**

```bash
# Terminal 2: Requisições subsequentes serão mais rápidas
echo "=== Segunda requisição (Warm) ==="
time curl -X POST http://api.cep.127.0.0.1.nip.io:8080 \
  -H "Content-Type: text/plain" \
  -d "01310100"

echo "=== Terceira requisição (Warm) ==="
time curl -X POST http://api.cep.127.0.0.1.nip.io:8080 \
  -H "Content-Type: text/plain" \
  -d "20040020"
```

**O que observar:**
- ⚡ **Latência reduzida:** Requisições subseqüentes são muito mais rápidas (< 1s)
- 📊 **Pod ativo:** Pod permanece "Running" enquanto há tráfego

**Passo 4: Aguardar scale-to-zero**

```bash
# Aguarde aproximadamente 30-60 segundos sem fazer requisições
# No Terminal 1, você verá o pod ser removido automaticamente
```

**O que observar:**
- ⏰ **Após ~30 segundos:** Pod muda para "Terminating"
- 🗑️ **Pod removido:** Pod desaparece completamente
- ✅ **Zero recursos:** Nenhum pod ativo = zero custo

### Demonstração 2: Observando o Auto-Scaling (Scale-Up)

**Objetivo:** Ver como a função escala automaticamente sob carga

**Preparação: Abrir 3 terminais**

**Terminal 1: Monitorar pods**
```bash
watch -n 1 'kubectl get pods -n cep -o wide'
```

**Terminal 2: Monitorar serviços Knative**
```bash
watch -n 1 'kn service list -n cep'
```

**Terminal 3: Gerar carga**

Instalar ferramenta de load testing:

```bash
# macOS
brew install hey

# Linux
wget https://hey-release.s3.us-east-2.amazonaws.com/hey_linux_amd64
chmod +x hey_linux_amd64
sudo mv hey_linux_amd64 /usr/local/bin/hey
```

**Executar teste de carga:**

No Terminal 3, execute:

```bash
# Teste 1: 50 requisições, 5 simultâneas (escala lenta)
echo "=== Teste 1: Carga moderada ==="
hey -n 50 -c 5 -m POST \
  -H "Content-Type: text/plain" \
  -d "71691058" \
  http://api.cep.127.0.0.1.nip.io:8080

# Aguardar alguns segundos...

# Teste 2: 200 requisições, 20 simultâneas (escala rápida)
echo "=== Teste 2: Carga alta ==="
hey -n 200 -c 20 -m POST \
  -H "Content-Type: text/plain" \
  -d "71691058" \
  http://api.cep.127.0.0.1.nip.io:8080
```

**O que observar nos terminais:**

**Terminal 1 (Pods):**
- 📈 **Escala crescendo:** Pods sendo criados rapidamente
- 🔢 **Múltiplos pods:** Vários pods em "Running"
- ⚡ **Distribuição:** Requisições distribuídas entre pods

**Terminal 2 (Serviços):**
- 📊 **Status:** URL do serviço permanece ativa
- 🎯 **Revisões:** Nova revisão pode aparecer se atualizar

**Terminal 3 (Resultados):**
- ⏱️ **Latência:** Tempo médio de resposta
- 📈 **Throughput:** Requisições por segundo
- ✅ **Success rate:** Taxa de sucesso

### Resumo da Demonstração

**O que foi observado:**
1. ✅ **Scale-to-Zero:** Função escalou para zero após inatividade
2. ✅ **Cold Start:** Primeira requisição demorou mais (pod sendo criado)
3. ✅ **Warm Requests:** Requisições subsequentes muito mais rápidas
4. ✅ **Auto-Scaling:** Função escalou automaticamente sob carga
5. ✅ **Zero Config:** Tudo automático, sem intervenção manual

**Benefícios para o desenvolvedor:**
- 🚀 **Deploy simples:** Apenas `func deploy` ou `kn service create`
- ⚡ **Performance automática:** Escalabilidade gerenciada pela plataforma
- 💰 **Custo eficiente:** Paga apenas quando há requisições
- 🔧 **Foco no código:** Zero preocupação com infraestrutura

---

## ✅ Conclusões

### 📐 Perspectiva de Infraestrutura

✅ **Knative é uma solução robusta para serverless on-premise**
- Projeto CNCF maduro e estável
- Sem vendor lock-in, total portabilidade
- Integração nativa com Kubernetes
- Escalabilidade automática baseada em métricas reais

✅ **Configuração e operação são diretas**
- Instalação via YAML (declarativa)
- Componentes bem definidos e observáveis
- Configuração flexível para diferentes cenários
- Troubleshooting facilitado por métricas nativas

✅ **Knative complementa Kubernetes perfeitamente**
- Aproveita recursos existentes do cluster
- Funciona com ferramentas de observabilidade existentes
- Suporta diferentes ingress controllers (Kourier, Istio)
- Integra com CI/CD pipelines

### 👨‍💻 Perspectiva do Desenvolvedor

✅ **FaaS simplifica significativamente o desenvolvimento**
- Foco total na lógica de negócio
- Zero preocupação com infraestrutura
- Deploy extremamente rápido e simples

✅ **Experiência de desenvolvimento otimizada**
- Templates prontos para iniciar rapidamente
- Build e deploy automatizados
- Hot reload e teste local facilitado
- Suporte a múltiplas linguagens (Java, Go, Node, Python, etc.)

✅ **Escalabilidade automática funciona na prática**
- Scale-to-zero economiza recursos
- Escala rápida sob demanda sem intervenção
- Cold start gerenciado automaticamente
- Performance otimizada para workloads event-driven

✅ **Produtividade aumentada**
- Deploy em minutos vs horas/dias
- Menos configuração = menos erros
- Foco no código de negócio
- Ciclo de feedback mais rápido

### Benefícios Demonstrados

| Aspecto | Benefício |
|---------|-----------|
| **Custos** | Redução de 60-80% em cenários com tráfego variável |
| **Deploy** | Minutos do código à produção |
| **Operações** | 70-90% menos tempo com infraestrutura |
| **Escalabilidade** | Automática de 0 a N instâncias |
| **Manutenção** | Zero manutenção de servidores |

### Quando Usar FaaS?

✅ **Ideal para:**
- APIs REST leves e rápidas
- Processamento de eventos assíncronos
- Microserviços com tráfego variável
- Integrações e webhooks
- Processamento de dados sob demanda

❌ **Não ideal para:**
- Aplicações com estado persistente
- Workloads com processamento longo (>15 min)
- Aplicações que requerem conexões persistentes
- Funções com cold start crítico (latência <100ms)

---

## 🔮 Próximos Passos

### Nível 1: Explorar Knative Eventing

Implementar arquitetura event-driven:

```bash
# Instalar Knative Eventing
kubectl apply -f https://github.com/knative/eventing/releases/download/knative-v1.19.7/eventing-crds.yaml
kubectl apply -f https://github.com/knative/eventing/releases/download/knative-v1.19.7/eventing-core.yaml

# Criar função que processa eventos
kn func create -l springboot -t cloudevents processador-eventos
```

**Casos de uso:**
- Webhooks de GitHub/GitLab
- Processamento de eventos de filas
- Arquitetura pub/sub

### Nível 2: Integração com Filas e Mensageria

**Kafka Integration:**
```bash
# Instalar Kafka Source para Knative
kubectl apply -f https://github.com/knative-sandbox/eventing-kafka-broker/releases/download/knative-v1.19.0/eventing-kafka-controller.yaml
```

**RabbitMQ Integration:**
- Usar Knative Eventing Sources
- Criar triggers para processar mensagens

### Nível 3: CI/CD para Funções Serverless

**Exemplo com GitHub Actions:**

```yaml
name: Deploy Function
on:
  push:
    branches: [main]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup Func CLI
        run: |
          wget https://github.com/knative/func/releases/latest/download/func_linux_amd64
          chmod +x func_linux_amd64
          sudo mv func_linux_amd64 /usr/local/bin/func
      - name: Deploy
        run: |
          func deploy --registry docker.io/diogocarvalho/faas-demo --namespace cep
```

### Nível 4: Observabilidade e Monitoramento

**Instalar Prometheus e Grafana:**

```bash
# Instalar Prometheus Operator
kubectl apply -f https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/main/bundle.yaml

# Configurar métricas do Knative
kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.19.7/serving-core.yaml
```

**Métricas importantes:**
- Request rate (req/s)
- Latência (p50, p95, p99)
- Cold start duration
- Pod count (escalabilidade)
- Error rate

**Dashboard Grafana:**
- Criar dashboards para visualizar métricas
- Alertas para cold starts longos
- Monitoramento de custos (pod-hours)

### Nível 5: Segurança e Boas Práticas

**Implementar:**
- Network policies para isolamento
- Secrets management (Vault/Sealed Secrets)
- Image scanning (Trivy, Snyk)
- RBAC (Role-Based Access Control)
- mTLS entre serviços (Istio)

### Recursos Adicionais

📚 **Documentação Oficial:**
- [Knative Documentation](https://knative.dev/docs/)
- [Spring Cloud Function](https://spring.io/projects/spring-cloud-function)
- [Knative Serving Examples](https://github.com/knative/docs/tree/main/docs/serving/samples)

🎓 **Tutoriais:**
- [Knative Getting Started](https://knative.dev/docs/getting-started/)
- [Spring Cloud Function Guide](https://docs.spring.io/spring-cloud-function/docs/current/reference/html/)

💡 **Comunidade:**
- [Knative Slack](https://knative.slack.com/)
- [CNCF Slack](https://slack.cncf.io/)
- GitHub Discussions

---

## 🛠️ Troubleshooting Comum

### Problema: Pod não escala de zero

**Solução:**
```bash
# Verificar autoscaler
kubectl get podautoscaler -n cep

# Verificar logs do autoscaler
kubectl logs -n knative-serving -l app=autoscaler

# Verificar se Metrics Server está funcionando
kubectl top pods -n cep
```

### Problema: Cold start muito longo

**Soluções:**
- Usar `minScale: "1"` para manter um pod warm
- Otimizar tamanho da imagem Docker
- Considerar GraalVM native image
- Reduzir dependências

### Problema: Erro de conexão ao serviço

**Solução:**
```bash
# Verificar se Kourier está rodando
kubectl get pods -n kourier-system

# Verificar configuração de domínio
kubectl get configmap config-domain -n knative-serving

# Verificar port-forward
kubectl port-forward -n kourier-system service/kourier 8080:80
```

### Problema: Função retorna timeout

**Solução:**
```bash
# Aumentar timeout do Knative
kn service update api \
  --namespace cep \
  --annotation timeoutSeconds="300"
```


## 🙌 Encerramento

### Resumo da Apresentação Completa

#### 📐 Visão de Infraestrutura
1. ✅ Entendemos o funcionamento do FaaS e seus componentes
2. ✅ Configuramos o Knative em um cluster Kubernetes on-premise
3. ✅ Aprendemos sobre arquitetura, escalabilidade e componentes internos
4. ✅ Monitoramos e observamos o comportamento do sistema

#### 👨‍💻 Visão do Desenvolvedor
1. ✅ Criamos uma função serverless com Spring Boot
2. ✅ Implementamos a lógica de negócio (consulta de CEP)
3. ✅ Realizamos build e deploy da função no cluster
4. ✅ Testamos e validamos o funcionamento
5. ✅ Observamos escalabilidade automática e scale-to-zero em ação

### Contato

👨‍💻 **Diogo Carvalho de Matos**  
🏢 **Cargo:** Arquiteto de Software e Soluções  
📧 **Email:** diogo.matos@castgroup.com.br

💻 **Demo:** Spring Boot + Knative + Kubernetes On-Premise

### Recursos da Apresentação

- 📄 Este README completo
- 💻 Código da demonstração: [GitHub Repository]
- 🎥 Gravação: [Link se disponível]

---

**🚀 Obrigado pela atenção!**
