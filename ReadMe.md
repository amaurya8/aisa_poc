# Data Cloud Automation Framework - User Guide & Onboarding

## Introduction
The **Reusable Automation Framework** is a powerful, plug-and-play solution designed to simplify automation testing across multiple domains. It provides pre-built libraries for various automation needs, ensuring a seamless and efficient testing experience. The framework is designed for **easy onboarding, high reusability, and smooth execution**, supporting both local and remote test execution via GitLab pipelines.

## Key Features
- **Comprehensive Automation Support:** Includes libraries for **Unix, Azure cloud service automation, Big Data & Hadoop, API testing, Playwright (UI), Windows, Mobile, and Database automation**.
- **Azure Cloud Service Automation:** Provides specialized libraries for automating **Azure services**, including:
    - **Azure DataBricks** automation for data processing and workflows.
    - **Azure Data Lake Storage (ADLS)** integration for seamless file operations.
    - **Azure Data Explorer (ADX)** automation for querying and managing large datasets.
    - **Azure Virtual Machines (VMs)** provisioning, monitoring, and management.
    - **Azure Key Vault** and **HashiCorp Vault** integration for secure credential management.
- **Plug-and-Play Deployment:** All libraries are deployed on **Nexus**, allowing easy integration into `pom.xml`.
- **Behavior-Driven Development (BDD) & TestNG Support:** Uses **Cucumber and TestNG**, providing flexibility in choosing the test execution framework.
- **Spring & Non-Spring Project Compatibility:** Can be used seamlessly in **Spring and non-Spring based projects**.
- **Flexible Reporting Options:** Supports multiple test reporting formats for **better analysis and debugging**.
- **Prebuilt Reference Projects:** Includes **detailed references and working scripts** provided in separate reference projects.
- **Seamless Onboarding:** Well-documented and easy-to-use framework to **accelerate adoption**.
- **Remote Execution via GitLab Pipelines:** Enables **parallel execution and CI/CD integration**.
- **Cross-Platform Compatibility:** Supports execution across **multiple environments**.
- **Support Provided:** Dedicated support to assist with **setup, troubleshooting, and enhancements**.

## Benefits
- **Accelerated Test Automation:** Reduces development effort by providing **pre-built reusable libraries**.
- **Azure-Centric Automation:** Ensures smooth automation for **Azure cloud services and infrastructure**.
- **Increased Test Efficiency:** Ensures smooth execution with **minimum setup effort**.
- **Improved Maintainability:** Encourages **standardized** and **structured** test automation.
- **Scalability:** Supports **large-scale automation needs**, including **cloud-based executions**.
- **Flexibility & Customization:** Users can **extend and customize** libraries based on project needs.

---

# Onboarding Guide
## Prerequisites
Ensure you have the following setup:
- **Java 8+** installed
- **Maven** installed for dependency management
- **Git** installed for repository cloning
- **Access to Nexus Repository** (for fetching dependencies)
- **GitLab pipeline setup** (for CI/CD execution)
- **Azure Subscription** (for cloud service automation)

## Step-by-Step Setup
### 1. Clone the Repository
```bash
 git clone <repository-url>
```

### 2. Add Dependencies in `pom.xml`
```xml
<dependencies>
    <dependency>
        <groupId>com.automation</groupId>
        <artifactId>azure-automation</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.automation</groupId>
        <artifactId>unix-automation</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.automation</groupId>
        <artifactId>playwright-automation</artifactId>
        <version>1.0.0</version>
    </dependency>
    <!-- Add other required libraries -->
</dependencies>
```

### 3. Configure Cucumber Tests
Create a **feature file** (`src/test/resources/features/sample.feature`):
```gherkin
Feature: Sample Automation Test
  Scenario: Verify API response
    Given I send a request to the API
    When I receive the response
    Then The status code should be 200
```

### 4. Execute Tests Locally
Run tests using Maven or Data Cloud Runner:

```bash
mvn test
```

### 5. Execute Tests via GitLab Pipeline
Define the `.gitlab-ci.yml` file:
```yaml
stages:
  - test

run-automation-tests:
  stage: test
  script:
    - mvn test
  artifacts:
    paths:
      - target/reports/
```

---

# Azure Cloud Service Automation
The framework provides specialized capabilities for **Azure cloud service automation**, including:

### 1. Automating Azure DataBricks
```
AzureDataBricksClient client = new AzureDataBricksClient("<subscription-id>");
client.runNotebook("workspace/path/to/notebook");
```

### 2. Azure Data Lake Storage (ADLS) Operations
```
ADLSClient adls = new ADLSClient("<storage-account>", "<container>");
adls.uploadFile("local/path/to/file.txt", "remote/path/in/adls/");
```

### 3. Querying Azure Data Explorer (ADX)
```
ADXClient adx = new ADXClient("<adx-cluster-url>");
String result = adx.executeQuery("database-name", "Table | take 10");
```

### 4. Azure Virtual Machines (VMs) Automation
```
AzureVMClient vmClient = new AzureVMClient("<subscription-id>");
vmClient.startVM("vm-resource-group", "vm-name");
```

### 5. Secure Secrets Management with Azure & HashiCorp Vault
```
AzureKeyVaultClient keyVault = new AzureKeyVaultClient("<keyvault-name>");
String secret = keyVault.getSecret("my-secret-key");

HashiCorpVaultClient vault = new HashiCorpVaultClient("<vault-url>");
String hashicorpSecret = vault.getSecret("path/to/secret");
```

---

# Reporting & Test Analysis
The framework generates detailed execution reports located in:
```
target/reports/cucumber-html-report.html
```
Open the **HTML report** to analyze test execution results and logs.

---

# Reference Projects
For better onboarding and faster implementation, we provide **working reference projects** demonstrating various automation use cases, including:
- **API Automation with RestAssured**
- **UI Automation with Playwright & Selenium**
- **Database Automation with JDBC**
- **Azure Service Automation (Databricks, ADLS, ADX, Key Vault, VMs, HashiCorp Vault)**
- **Unix Automation with Shell Commands**

These reference projects serve as a **starting point** for users to integrate and customize the framework as per their needs.

---

# Conclusion
The **Reusable Automation Framework** simplifies test automation by providing ready-to-use libraries for multiple automation domains, with an **emphasis on Azure cloud service automation**. It ensures **scalability, efficiency, and seamless execution**, making it an ideal choice for enterprise automation needs.

** Lets Get started today and accelerate out automation journey!**

