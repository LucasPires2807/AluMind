CREATE TABLE feedback (
                           id UUID PRIMARY KEY,
                           text TEXT NOT NULL,
                           sentiment VARCHAR(20),
                           date_create TIMESTAMP DEFAULT NOW()
);

-- Inserindo dados na tabela public.users
INSERT INTO public.feedback (id, text, sentiment)
VALUES
    ('4f6d11c7-82cf-4d6a-a0c3-56a1f79149d1', 'Adorei a nova interface, ficou muito intuitiva.', 'POSITIVO'),
    ('a46d25c1-ec22-4d3b-b9a7-ec5ab3ac2df7', 'Os professores são excelentes, estão de parabéns.', 'POSITIVO'),
    ('9bfb08e7-3ae2-4b8e-b5b5-24f346b30972', 'O site está muito lento, precisa melhorar o desempenho.', 'NEGATIVO'),
    ('f64c1132-37c6-4045-a9b5-f5244938adbc', 'Não gostei do novo layout, está confuso.', 'NEGATIVO');