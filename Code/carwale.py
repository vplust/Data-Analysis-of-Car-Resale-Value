
import csv
import os
import requests
import threading
from bs4 import BeautifulSoup
import traceback

url_str = 'http://www.carwale.com/'
base_url = 'http://www.carwale.com'
company_names = ['audi', 'bmw', 'chevrolet', 'datsun', 'fiat',
'forcemotors', 'ford', 'honda', 'hyundai', 'jaguar', 'landrover',
'mahindra', 'marutisuzuki', 'mercedesbenz', 'mitsubishi', 'nissan',
'renault', 'skoda', 'tata', 'toyota', 'volkswagen', 'volvo']
companies = 22
cc = 0  # company-count

all_url = []
while cc < companies:
	all_url.append(url_str + company_names[cc] + '-cars/')
	cc = cc + 1

	full_list = []


def get_cars():
	for the_url in all_url:
		src = requests.get(the_url)
		soup = BeautifulSoup(src.text, 'lxml')
		for link in soup.findAll('a', {'class': 'font18'}):
			href = str('http://www.carwale.com' + link.get('href'))
			car_name = str(link.string)
			detail_list = []
			detail_list = car_detail(href)
			if detail_list[2] != -1:
				full_list.append(detail_list)
				print('added ' + detail_list[0] + ' ' + detail_list[1])
			else:
				print('price not found for ' + detail_list[0] + ' ' + detail_list[1])


def car_detail(car_url): 
	
	details = []
	src = requests.get(car_url)
	soup = BeautifulSoup(src.text, 'lxml')
	span_found = soup.findAll('span', {'itemprop': 'title'})
	comp_str = span_found[1].text
	car_str = soup.find('h1', 'font30 text-black').text
	price = 0
	for a in soup.findAll('a', {'class': 'font14 text-bold href-title text-grey'}):
		try:
			url = base_url + a['href']
			car_detail = requests.get(url)
			soup1 = BeautifulSoup(car_detail.text, 'lxml')
			# car_str = str(soup1.findAll(
			# 	'h1', {'class': 'font30 text-black'})[0].text.replace('\n', '').replace('\r', ''))
			soup_detail = soup1.findAll(
				'span', {'class': 'font24 text-black margin-right5'})
			str_price = soup_detail[0].text
			if str_price[-1] == 'L'	:
				int_price = int(float(str_price[:-1]) * 100000)
			else:
				int_price = int(float(str_price[:-2]) * 10000000)
			price = price+int_price
		except:
			traceback.print_exc()
			print ('in except')
			details.append(-1)
				# will remove the list out later
			return details
	avg_price  = price/len(soup.findAll('a', {'class': 'font14 text-bold href-title text-grey'}))
	details.extend([comp_str, car_str])
	details.append(avg_price)
	return details


if __name__ == '__main__':

	get_cars()
	outfile = open('./car_list.csv', 'w')
	writer = csv.writer(outfile)
	writer.writerow(['COMPANY NAME', 'CAR NAME', 'AVG. ON-ROAD PRICE'])
	writer.writerows(full_list)

	print('\nDONE !')
