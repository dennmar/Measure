FROM python:3.8.2-slim-buster

RUN mkdir /src
COPY . ./src 
WORKDIR /src

RUN pip install -r requirements.txt
EXPOSE 5000
ENV FLASK_APP app

CMD ["flask", "run", "--host", "0.0.0.0"]