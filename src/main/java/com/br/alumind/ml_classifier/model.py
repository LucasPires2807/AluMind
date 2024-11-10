import nltk
import string
import joblib

from nltk import tokenize
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import CountVectorizer

nltk.download('punkt')
nltk.download('stopwords')

# Dataset fictício
dataset = [
    ("Eu adoro este filme, é maravilhoso!", "positivo"),
    ("O serviço foi horrível, muito decepcionante", "negativo"),
    ("A experiência foi incrível, recomendo a todos", "positivo"),
    ("O produto é péssimo, nunca mais compro aqui", "negativo"),
    ("Atendimento excelente e rápido", "positivo"),
    ("Não gostei da comida, estava fria", "negativo"),
    ("A equipe foi muito atenciosa", "positivo"),
    ("O local é barulhento e desconfortável", "negativo")
]

def clean_text(text):
    # Remover pontuação e tokenizar
    stop_words = nltk.corpus.stopwords.words("portuguese")
    text = text.translate(str.maketrans('', '', string.punctuation))
    space_tokenizer = tokenize.WhitespaceTokenizer()
    words_tokenize = space_tokenizer.tokenize(text.lower())
    filtered_words = [word for word in words_tokenize if word not in stop_words]
    return ' '.join(filtered_words)  # Retorna o texto limpo como uma string

def classifier_texts(texts, labels):
    # Pré-processar os textos usando clean_text()
    cleaned_texts = [clean_text(text) for text in texts]
    print("clean texts", cleaned_texts)
    
    # Criando o vetorizador
    vetorizer = CountVectorizer(lowercase=False, max_features=50)
    bag_of_words = vetorizer.fit_transform(cleaned_texts)
    
    # Dividir em conjunto de treino e teste
    treino, teste, classe_treino, classe_teste = train_test_split(
        bag_of_words, labels, test_size=0.3, random_state=42
    )
    
    # Criando o modelo
    model = MultinomialNB()
    
    # Treinamento
    model.fit(treino, classe_treino)

    # Avaliação do modelo
    predicoes = model.predict(teste)
    acuracia = accuracy_score(classe_teste, predicoes)
    print(f"Acurácia do modelo: {acuracia:.2f}")
    
    return model, vetorizer

# Separando textos e labels
texts = [texto for texto, _ in dataset]
labels = [label for _, label in dataset]

# Classificação
model, vetorizer = classifier_texts(texts, labels)

# Testando com novos textos
new_text = ["O filme foi excelente e muito divertido"]
cleaned_new_text = [clean_text(text) for text in new_text]  # Limpeza do novo texto
text_vetorize = vetorizer.transform(cleaned_new_text)
print("Classificação do novo texto:", model.predict(text_vetorize))
