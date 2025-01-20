import csv
import os
import pandas as pd
import hopsworks
# Nome do arquivo original e do arquivo tratado
input_file = "datasets/twitter_feedbacks.csv"
output_file = "datasets/dataset_atualizado.csv"
# Faça login no Hopsworks
project = hopsworks.login()

def cleanDataset():
    # Ler o arquivo CSV
    df = pd.read_csv(input_file)
    # Converter os valores da coluna "Sentiment" para letras minúsculas
    df["SENTIMENT"] = df["SENTIMENT"].str.lower()
    # Gerando um ID numérico para cada linha
    df['DOC_ID'] = range(1, len(df) + 1)
    # Selecionando as colunas desejadas
    selected_columns = df[['TEXT', 'SENTIMENT', 'SOURCE', 'DOC_ID']]
    # Salvando o resultado em um novo arquivo CSV
    selected_columns.to_csv(output_file, index=False)
    print(f"Arquivo tratado foi salvo como '{output_file}'!")

def uploadFile(group_name):
    # Fazer o upload do arquivo para o Hopsworks
    dataset_api = project.get_dataset_api()
    fs = project.get_feature_store()
    # Ler o CSV
    df = pd.read_csv(output_file)
    dataset_api.upload(output_file, "Resources/", overwrite=True)
    print("Upload do arquivo CSV realizado com sucesso!")

     # Criar o Feature Group
    feature_group = fs.get_or_create_feature_group(
        name=group_name,
        version=1,
        description="Dados de treinamento do Twitter para análise de sentimentos",
        primary_key=["DOC_ID"],  # Substitua pelo nome da coluna com identificadores únicos, se houver
        online_enabled=False
    )

def downloadFile():
    dataset_api = project.get_dataset_api()
    # Caminho do arquivo no Hopsworks (substitua pelo caminho correto)
    hopsworks_file_path = "Resources/" +  output_file # Exemplo de caminho
    # Caminho local para salvar o arquivo no Colab
    local_file_path = os.path.join(os.getcwd(), "datasets")
    # Fazer o download do arquivo
    dataset_api.download(hopsworks_file_path, local_file_path)
    print(f"Arquivo baixado e salvo em: {local_file_path}")
    #print(df.info())

#tratar arquiv
#enviar para hopsworks
#salvar arquivo
#cleanDataset()
uploadFile("dataset_update")
 