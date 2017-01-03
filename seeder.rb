#!/usr/bin/env ruby

require 'bcrypt'
require 'mysql2'

client = Mysql2::Client.new(
  :host => 'localhost',
  :username => 'root',
  :password => 'codeup',
  :database => 'blog_db'
)

users = [
  ['zach', 'zach@codeup.com', 'codeup'],
  ['ryan', 'ryan@codeup.com', 'codeup'],
  ['fernando', 'fernando@codeup.com', 'codeup'],
  ['luis', 'luis@codeup.com', 'codeup'],
  ['justin', 'justin@codeup.com', 'codeup']
]

stmt = client.prepare('INSERT INTO users(username, email, password) VALUES (?, ?, ?)')

users.each do |user|
  stmt.execute(
    user[0],
    user[1],
    BCrypt::Password.create(user[2])
  )
end

users = client.query('select * from users').to_a

ids = users.map {|user| user['id']}

ids.each do |id|
  client.query("INSERT into user_role(user_id, role) VALUES (#{id}, 'user')")
end

stmt = client.prepare('insert into posts(user_id, title, body) values (?, ?, ?)')

7.times do
  user = users.sample
  stmt.execute(
    user['id'],
    "This is #{user['username']}'s post!",
    "lorem ipsum etc...."
  )
end

id = client.query("select * from users where username = 'zach'").to_a.first['id']
client.query("insert into user_role(user_id, role) values (#{id}, 'admin')")

%w(food coffee life programming).each do |tag|
  client.query("insert into tags(name) values ('#{tag}')")
end

