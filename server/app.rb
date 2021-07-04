require 'sinatra'
require 'json'
require 'date'

EXPIRED_ACCESS_TOKEN_SECONDS = 30
EXPIRED_REFERSH_TOKEN_SECONDS = 60

get '/' do
    'Hello World!'
end

post '/login' do
    now = Time.now.to_i
    content_type :json
    auth_token = {
        access_token: "#{now}",
        refresh_token: "#{now}"
    }
    auth_token.to_json
end

post '/refresh_token' do
    refresh_token_active?

    now = Time.now.to_i
    content_type :json
    auth_token = {
        access_token: "#{now}",
        refresh_token: "#{now}"
    }
    auth_token.to_json
end

get '/user' do
    authorized?
    
    content_type :json
    user = {
        id: 1,
        name: "User 1"
    }
    user.to_json
end

def authorized?
    headers = request.env.select do |key, val|
        key.start_with?("HTTP_")
    end
    access_token = headers["HTTP_AUTHORIZATION"]
    if access_token.nil? || access_token.empty?
        halt 401, 'Unauthorized'
    end

    time = access_token.sub(/Bearer /, '').to_i
    now = Time.now.to_i
    if (time + EXPIRED_ACCESS_TOKEN_SECONDS) <= now
        halt 401, 'Unauthorized: Expired access token'
    end
end

def refresh_token_active?
    refresh_token = params['refresh_token']
    time = refresh_token.to_i

    now = Time.now.to_i
    if (time + EXPIRED_REFERSH_TOKEN_SECONDS) <= now
        halt 400, 'Refresh Token expired'
    end
end