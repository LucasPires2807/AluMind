# predict.py
import sys
import joblib

# Carregar o modelo e o vetorizador previamente salvos
model = joblib.load('feedback_classifier.pkl')

# Obter o texto do argumento passado pelo Java
text = sys.argv[1]

# Transformar o texto em vetores numéricos
prediction = model.predict(text)[0]

# Imprimir a predição (positivo ou negativo)
print(prediction)
