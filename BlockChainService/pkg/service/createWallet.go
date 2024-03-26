package service

import (
	"BlockChainService"
	"hg.sr.ht/~dchapes/ripple/crypto/rkey"
)

func (s *Service) CreateWallet(requestWallet BlockChainService.RequestWallet) (*rkey.FamilySeed, string) {
	secret, addr := generateWallet()
	return secret., addr
}

func generateWallet() (*rkey.FamilySeed, string) {
	s, err := rkey.GenerateSeed()
	if err != nil {
		return nil, ""
	}
	addr := s.PrivateGenerator.PublicGenerator.Generate(0).Address()
	return addr
}
