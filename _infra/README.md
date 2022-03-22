# gcloud and kubernetes commands

## GCP configs
GCP configurations are like profiles, we can apply settings for different projects and
switch contexts easily.

### Useful commands :

See current active config info : `gcloud info`

List all configurations : `gcloud config configurations list`

Change gcloud configuration : `gcloud config configurations activate prod`

Set current account for config : `gcloud config set account 'jacobus.algera@gmail.com'`

### Setup new gcloud configuration :
- Login : `gcloud auth login`
- Init profile and choose config name and project : `gcloud init`
- Make sure correct config is active : `gcloud config configurations activate CONFIG_NAME`
- List all available Kubernetes clusters : `gcloud container clusters list` 
- Get creds for cluster and store for use with kubectl : `gcloud container clusters get-credentials --zone <ZONE_NAME> <CLUSTER_NAME>`

## Glossary

KSA: Kubernetes Service Account
