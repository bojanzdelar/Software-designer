FROM python:3.10

WORKDIR /app

COPY Pipfile Pipfile.lock /app/

RUN pip install --no-cache-dir pipenv && \
    pipenv install --deploy --ignore-pipfile

COPY . /app

CMD ["pipenv", "run", "gunicorn", "-w", "4", "app:app", "-b", "0.0.0.0:8000"]