# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box     = "trusty64"
  config.vm.box_url = "https://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box"
  #config.vm.box_url = "trusty-server-cloudimg-amd64-vagrant-disk1.box"
  config.vm.network :forwarded_port, :guest => 2181, :host => 2181
  config.vm.network :forwarded_port, :guest => 1883, :host => 1883
  config.vm.network :forwarded_port, :guest => 9092, :host => 9092
  config.vm.network :forwarded_port, :guest => 8080, :host => 8080
  config.vm.provision :shell, path: "install.sh"
  config.vm.synced_folder "repositorio/", "/home/vagrant"
  
  config.vm.provider "virtualbox" do |v|
	v.memory = 4096
	v.cpus = 2
   end
end

