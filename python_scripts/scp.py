import pandas as pd

import pandas as pd

def update_sentiment(input_file, output_file):
    try:
        # Carregar o dataset
        df = pd.read_csv(input_file)

        # Verificar se as colunas necessárias existem
        if 'score' not in df.columns:
            print("A coluna 'score' não foi encontrada no dataset.")
            return
        if 'sentiment' not in df.columns:
            df['sentiment'] = ''  # Adicionar a coluna de sentimento, se não existir

        # Atualizar a coluna 'sentiment' com base no valor de 'score'
        df['sentiment'] = df['score'].apply(lambda x: 'negativo' if x < 3 else 'positivo' if x > 3 else 'Neutro')

        # Salvar o dataset atualizado
        df.to_csv(output_file, index=False)
        print(f"Dataset atualizado salvo em: {output_file}")
        return df

    except Exception as e:
        print(f"Erro ao processar o arquivo: {e}")


def remove_column(file_path, column_name, output_path):
    try:
        # Carregar o dataset
        df = pd.read_csv(file_path)
        
        # Verificar se a coluna existe no dataset
        if column_name in df.columns:
            # Remover a coluna
            df.drop(columns=[column_name], inplace=True)
            print(f"Coluna '{column_name}' removida com sucesso!")
        else:
            print(f"A coluna '{column_name}' não foi encontrada no dataset.")
        
        # Salvar o dataset atualizado
        df.to_csv(output_path, index=False)
        print(f"Dataset atualizado salvo em: {output_path}")
    except Exception as e:
        print(f"Erro ao processar o arquivo: {e}")


# Exemplo de uso
input_path = "datasets/reviews_dataset.csv"  # Substitua pelo caminho do arquivo de entrada
output_path = "datasets/dataset_atualizado.csv"  # Substitua pelo caminho do arquivo de saída

# Chamar a função
updated_df = update_sentiment(input_path, output_path)
 
for column_name in ['reviewId', 'userImage','thumbsUpCount', 'reviewCreatedVersion', 'replyContent','repliedAt','sortOrder','appId']:
    
    input_file = output_path  
    column_to_remove = column_name        
    output_file = output_path    
    remove_column(input_file, column_to_remove, output_file)

