import pandas as pd

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
 
for column_name in ['reviewId', 'userImage','thumbsUpCount', 'reviewCreatedVersion', 'replyContent','repliedAt','sortOrder','appId']:
    
    input_file = "dataset_atualizado.csv"  
    column_to_remove = column_name        
    output_file = "dataset_atualizado.csv"    
    remove_column(input_file, column_to_remove, output_file)
