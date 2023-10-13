# (RE)Sources Realtionnelles

## Setup

The first thing to do is to clone the repository:

```sh
$ git clone https://github.com/CESIProjects/WebApp-Backend.git
$ cd WebApp-Backend
```

Create a virtual environment to install dependencies in and activate it:

```sh
$ python3 -m venv env
$ source env/bin/activate
```

Then install the dependencies:

```sh
(env)$ python -m pip install --upgrade pip
(env)$ pip install django
(env)$ pip install djangorestframework
```
Note the `(env)` in front of the prompt. This indicates that this terminal
session operates in a virtual environment set up by `virtualenv2`.

Once `pip` has finished downloading the dependencies:
```sh
(env)$ cd backend
(env)$ python manage.py runserver
```
And navigate to `http://127.0.0.1:8000/`.


## Test

Before doing anything to our BEAUTIFUL APP check if `http://127.0.0.1:8000` return
home page, if it does then you can try to write some new request !!