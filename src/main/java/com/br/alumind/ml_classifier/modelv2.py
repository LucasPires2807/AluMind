import nltk
import spacy
import re
from nltk.corpus import stopwords

# Baixar os dados necessários do NLTK
nltk.download('punkt')
nltk.download('stopwords')
nltk.download('punkt_tab')


# Carregar o modelo spaCy
nlp = spacy.load("pt_core_news_sm")  # Usando o modelo em português

# Stopwords em português
stop_words = set(stopwords.words('portuguese'))

# Função para pré-processar o texto
def preprocess_text(text):
    # Converter para minúsculas
    text = text.lower()
    # Remover caracteres especiais
    text = re.sub(r'[^a-záàãâäéèêíìïóòõôöúùûüç\s]', '', text)
    # Tokenizar
    tokens = nltk.word_tokenize(text, language='portuguese')
    # Remover stopwords e lemmatizar
    tokens = [token.lemma_ for token in nlp(' '.join(tokens)) if token.lemma_ not in stop_words]
    return ' '.join(tokens)

# Função para extrair funcionalidades
def extract_functionalities(text):
    doc = nlp(text)
    functionalities = []
    # Extrair entidades que são funcionais (como 'produto', 'atendimento', etc.)
    # Lista de palavras-chave que podem indicar funcionalidades sugeridas
    keywords = ['opção', 'função', 'recurso', 'melhoria', 'filtro', 'comparar', 'pesquisa', 'customizar', 'notificação', 'recomendação']
    
    for sent in doc.sents:
        # Verifica se alguma das palavras-chave está na frase
        if any(keyword in sent.text.lower() for keyword in keywords):
            functionalities.append(sent.text)
    return functionalities

# Dataset fictício
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

# Classificar os feedbacks e extrair funcionalidades
for feedback, sentiment, functionalities in dataset:
    print(f"Feedback: {feedback}")
    print(f"Sentimento: {sentiment}")
    # Pré-processar o feedback
    processed_feedback = preprocess_text(feedback)
    # Extrair funcionalidades sugeridas
    functionalities = extract_functionalities(processed_feedback)
    print(f"Funcionalidades sugeridas: {functionalities}\n")
