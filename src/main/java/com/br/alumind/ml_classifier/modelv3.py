import nltk
import string
import joblib
from nltk import tokenize
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import CountVectorizer

# Baixar pacotes do NLTK
nltk.download('punkt')
nltk.download('stopwords')

# Dataset fictício com polaridade e funcionalidades
dataset = [
    ("Eu adoro este filme, é maravilhoso!", "positivo", "não funcionalidade"),
    ("O serviço foi horrível, muito decepcionante", "negativo", "não funcionalidade"),
    ("A experiência foi incrível, recomendo a todos", "positivo", "não funcionalidade"),
    ("O produto é péssimo, nunca mais compro aqui", "negativo", "não funcionalidade"),
    ("Atendimento excelente e rápido", "positivo", "não funcionalidade"),
    ("Não gostei da comida, estava fria", "negativo", "não funcionalidade"),
    ("A equipe foi muito atenciosa", "positivo", "não funcionalidade"),
    ("O local é barulhento e desconfortável", "negativo", "não funcionalidade"),
    ("Seria ótimo ter uma opção de filtro de pesquisa para produtos.", "positivo", "funcionalidade"),
    ("Adicionar uma funcionalidade de comparação de preços entre os produtos.", "positivo", "funcionalidade"),
    ("Eu gostaria de uma opção para salvar itens no carrinho para comprá-los depois.", "positivo", "funcionalidade"),
    ("Uma funcionalidade de avaliação dos produtos por estrelas seria útil.", "positivo", "funcionalidade"),
    ("Seria bom se houvesse uma opção de pagamento parcelado.", "positivo", "funcionalidade"),
    ("Preciso de uma função para acompanhar o status de entrega dos pedidos.", "positivo", "funcionalidade"),
    ("Adicionar uma opção para personalizar as notificações.", "positivo", "funcionalidade"),
    ("Uma função de recomendação de produtos com base nas compras anteriores seria ótima.", "positivo", "funcionalidade")
]

def clean_text(text):
    # Remover pontuação e tokenizar
    stop_words = nltk.corpus.stopwords.words("portuguese")
    text = text.translate(str.maketrans('', '', string.punctuation))
    space_tokenizer = tokenize.WhitespaceTokenizer()
    words_tokenize = space_tokenizer.tokenize(text.lower())
    filtered_words = [word for word in words_tokenize if word not in stop_words]
    return ' '.join(filtered_words)  # Retorna o texto limpo como uma string

def classifier_texts(texts, polarity_labels, functionality_labels):
    # Pré-processar os textos usando clean_text()
    cleaned_texts = [clean_text(text) for text in texts]
    print("clean texts", cleaned_texts)
    
    # Criando o vetorizador
    vetorizer = CountVectorizer(lowercase=False, max_features=50)
    bag_of_words = vetorizer.fit_transform(cleaned_texts)
    
    # Dividir em conjunto de treino e teste
    treino, teste, classe_treino_polaridade, classe_teste_polaridade, classe_treino_funcionalidade, classe_teste_funcionalidade = train_test_split(
        bag_of_words, polarity_labels, functionality_labels, test_size=0.3, random_state=42
    )
    
    # Criando os modelos
    model_polaridade = MultinomialNB()
    model_funcionalidade = MultinomialNB()
    
    # Treinamento
    model_polaridade.fit(treino, classe_treino_polaridade)
    model_funcionalidade.fit(treino, classe_treino_funcionalidade)

    # Avaliação dos modelos
    predicoes_polaridade = model_polaridade.predict(teste)
    predicoes_funcionalidade = model_funcionalidade.predict(teste)
    
    acuracia_polaridade = accuracy_score(classe_teste_polaridade, predicoes_polaridade)
    acuracia_funcionalidade = accuracy_score(classe_teste_funcionalidade, predicoes_funcionalidade)
    
    print(f"Acurácia do modelo de polaridade: {acuracia_polaridade:.2f}")
    print(f"Acurácia do modelo de funcionalidade: {acuracia_funcionalidade:.2f}")
    
    return model_polaridade, model_funcionalidade, vetorizer

# Separando textos, polaridade e funcionalidade
texts = [texto for texto, _, _ in dataset]
polarity_labels = [polaridade for _, polaridade, _ in dataset]
functionality_labels = [funcionalidade for _, _, funcionalidade in dataset]

# Classificação
model_polaridade, model_funcionalidade, vetorizer = classifier_texts(texts, polarity_labels, functionality_labels)

# Testando com novos textos
new_text = ["O filme foi excelente e muito divertido. Seria ótimo ter mais filmes semelhantes."]
cleaned_new_text = [clean_text(text) for text in new_text]  # Limpeza do novo texto
text_vetorize = vetorizer.transform(cleaned_new_text)

# Predição da polaridade e funcionalidade
predicao_polaridade = model_polaridade.predict(text_vetorize)
predicao_funcionalidade = model_funcionalidade.predict(text_vetorize)

print("Classificação do novo texto:")
print(f"Polaridade: {predicao_polaridade[0]}")
print(f"Funcionalidade sugerida: {predicao_funcionalidade[0]}")
